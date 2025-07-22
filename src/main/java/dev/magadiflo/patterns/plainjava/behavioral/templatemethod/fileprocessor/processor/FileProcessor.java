package dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.processor;

import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.BusinessValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.ParsedRecord;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.ProcessResult;
import dev.magadiflo.patterns.plainjava.behavioral.templatemethod.fileprocessor.dto.ValidationResult;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class FileProcessor<T extends ParsedRecord> {

    public final ProcessResult processFile(String filePath, String batchId) {
        try {
            log.info("=== Iniciando procesamiento de archivo ===");
            log.info("Archivo: {} - Batch ID: {}", filePath, batchId);

            // Paso 1: Obtener y validar archivo
            Optional<Path> optionalPath = this.obtainFile(filePath);
            if (optionalPath.isEmpty()) {
                return ProcessResult.failure(batchId, "Archivo no encontrado o inaccesible");
            }

            // Paso 2: Validar estructura del archivo
            Path path = optionalPath.get();
            ValidationResult validation = this.validateFileStructure(path);
            if (!validation.valid()) {
                return ProcessResult.failure(batchId, "Validación fallida: ".concat(validation.errorMessage()));
            }

            // Paso 3: Procesar el contenido línea por línea
            List<T> parsedRecords = this.parseFileContent(path);
            if (parsedRecords.isEmpty()) {
                return ProcessResult.failure(batchId, "El archivo está vacío o no contiene registros procesables");
            }
            int totalRecords = parsedRecords.size();

            // Paso 4: Validar datos de negocio
            BusinessValidationResult<T> businessValidationResult = this.validateBusinessRules(parsedRecords);
            List<T> validRecords = businessValidationResult.validRecords();
            int invalidCount = businessValidationResult.invalidCount();
            int processedRecords = 0;

            // Paso 5: Persistir en base de datos
            ProcessResult result;
            if (!validRecords.isEmpty()) {
                processedRecords = this.storeRecords(validRecords, batchId);
                result = ProcessResult.success(batchId, totalRecords, processedRecords, invalidCount);
            } else {
                result = ProcessResult.failure(batchId, "No se encontraron registros válidos para almacenar.");
            }

            // Paso 6: Generar notificación final
            this.generateProcessingReport(result, path);

            if (result.success()) {
                log.info("Procesamiento finalizado exitosamente. Registros procesados: {}", result.processedRecords());
            } else {
                log.warn("Procesamiento finalizado con errores. Motivo: {}", result.errorMessage());
            }
            return result;
        } catch (Exception e) {
            log.error("Error durante procesamiento: {}", e.getMessage(), e);
            return ProcessResult.failure(batchId, "Error técnico: " + e.getMessage());
        }
    }

    // ============== MÉTODOS CONCRETOS (comunes a todos los tipos) ==============
    protected Optional<Path> obtainFile(String filePath) {
        try {
            Path path = Path.of(filePath);

            if (Files.notExists(path) || !Files.isReadable(path)) {
                log.warn("El archivo no existe o no es legible: {}", filePath);
                return Optional.empty();
            }

            log.info("Archivo obtenido exitosamente. Tamaño: {} bytes", Files.size(path));
            return Optional.of(path);
        } catch (Exception e) {
            log.warn("Error obtenido en archivo: {}", e.getMessage());
            return Optional.empty();
        }
    }

    protected void generateProcessingReport(ProcessResult result, Path path) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String format = LocalDateTime.now().format(formatter);

        String fileName = path.getFileName().toString();
        int index = fileName.lastIndexOf(".");
        String extension = fileName.substring(index + 1).toUpperCase();

        String state;
        if (!result.success()) {
            state = "Error";
        } else if (result.invalidRecords() > 0) {
            state = "Exitoso con advertencias";
        } else {
            state = "Exitoso";
        }

        StringBuilder sb = new StringBuilder("=== REPORTE DE PROCESAMIENTO %s ===".formatted(extension));
        sb.append("\n");
        sb.append("Archivo: ").append(path.toAbsolutePath()).append("\n");
        sb.append("Batch Id: ").append(result.batchId()).append("\n");
        sb.append("Fecha/Hora: ").append(format).append("\n");
        sb.append("Total registros: ").append(result.totalRecords()).append("\n");
        sb.append("Registros procesados: ").append(result.processedRecords()).append("\n");
        sb.append("Registros inválidos: ").append(result.invalidRecords()).append("\n");
        sb.append("Estado: ").append(state);

        log.info("REPORTE {}:\n{}", extension, sb);
    }

    // ============== MÉTODOS ABSTRACTOS (específicos por tipo de archivo) ==============
    protected abstract ValidationResult validateFileStructure(Path path);

    protected abstract List<T> parseFileContent(Path path) throws IOException;

    protected abstract BusinessValidationResult<T> validateBusinessRules(List<T> parsedRecords);

    protected abstract int storeRecords(List<T> validRecords, String batchId) throws SQLException;
}
