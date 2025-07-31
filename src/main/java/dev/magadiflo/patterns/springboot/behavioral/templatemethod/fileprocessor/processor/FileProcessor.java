package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.processor;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.FileType;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.exception.FileProcessorException;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.BusinessValidationResult;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.FileStorageInfo;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ParsedRecord;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ProcessResult;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ValidationResult;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.FileStorageService;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public abstract class FileProcessor<T extends ParsedRecord> {

    private final FileStorageService fileStorageService;

    public final ProcessResult processFile(MultipartFile multipartFile, String batchId) {
        log.info("=== Iniciando procesamiento de archivo ===");
        log.info("Archivo: {} - Batch ID: {} - Tamaño: {}", multipartFile.getOriginalFilename(), batchId, multipartFile.getSize());
        Path pathTempFile = null;

        try {
            // Paso 1: Validar archivo a procesar (lanza excepción si falla)
            this.validateMultipartFile(multipartFile);
            log.info("Archivo {} validado correctamente", multipartFile.getOriginalFilename());

            // Paso 2: Almacenar el archivo y generar uno temporal
            FileStorageInfo fileStorageInfo = this.fileStorageService.storeFile(multipartFile, batchId);
            log.info("Archivo {} guardado con éxito como {}", multipartFile.getOriginalFilename(), fileStorageInfo.filename());

            InputStream inputStream = this.fileStorageService.retrieveFileContent(fileStorageInfo);
            pathTempFile = FileUtils.retrieveAsTempFile(inputStream, fileStorageInfo.filename());
            log.info("Procesando archivo desde: {}", pathTempFile);

            // Paso 3: Validar estructura del archivo (ProcessResult si falla)
            ValidationResult structureValidation = this.validateFileStructure(pathTempFile);
            if (!structureValidation.valid()) {
                log.warn("Validación de estructura fallida: {}", structureValidation.errorMessage());
                return ProcessResult.failure(batchId, "Validación de estructura fallida: " + structureValidation.errorMessage(), fileStorageInfo);
            }

            // Paso 4: Procesar contenido línea por línea (ProcessResult si falla)
            List<T> parsedRecords = this.parseFileContent(pathTempFile);
            if (parsedRecords.isEmpty()) {
                log.warn("El archivo no contiene registros procesables");
                return ProcessResult.failure(batchId, "El archivo está vacío o no contiene registros procesables", fileStorageInfo);
            }
            int totalRecords = parsedRecords.size();
            log.info("Se parsearon {} registros del archivo", totalRecords);

            // Paso 5: Validar datos del negocio (ProcessResult con advertencias)
            BusinessValidationResult<T> businessValidationResult = this.validateBusinessRules(parsedRecords);
            List<T> validRecords = businessValidationResult.validRecords();
            int invalidCount = businessValidationResult.invalidCount();

            if (invalidCount > 0) {
                log.warn("Se encontraron {} registros inválidos de {}", invalidCount, totalRecords);
            }

            // Paso 6: Persistir en base de datos (ProcessResult si falla)
            ProcessResult result;
            if (!validRecords.isEmpty()) {
                log.info("Persistiendo {} registros válidos en base de datos", validRecords.size());
                int processedRecords = this.storeRecords(validRecords, batchId);
                result = ProcessResult.success(batchId, totalRecords, processedRecords, invalidCount, fileStorageInfo);
                log.info("Se persistieron {} registros exitosamente", processedRecords);
            } else {
                log.warn("No se encontraron registros válidos para almacenar");
                result = ProcessResult.failure(batchId, "No se encontraron registros válidos para almacenar.", fileStorageInfo);
            }

            // Paso 7: Generar notificación final
            this.generateProcessingReport(result, multipartFile);

            if (result.success()) {
                log.info("Procesamiento finalizado exitosamente. Registros procesados: {}", result.processedRecords());
            } else {
                log.warn("Procesamiento finalizado con errores. Motivo: {}", result.errorMessage());
            }
            return result;
        } finally {
            // Paso 8: Eliminar archivo temporal
            if (pathTempFile != null) {
                log.info("Limpiando archivo temporal: {}", pathTempFile);
                FileUtils.deleteTempFile(pathTempFile);
            }
        }
    }

    // ============== MÉTODOS CONCRETOS (comunes a todos los tipos) ==============
    protected void validateMultipartFile(MultipartFile multipartFile) {
        if (Objects.isNull(multipartFile) || multipartFile.isEmpty()) {
            throw new FileProcessorException("El archivo está vacío o no fue enviado");
        }

        String filename = multipartFile.getOriginalFilename();
        if (Objects.isNull(filename) || filename.isBlank()) {
            throw new FileProcessorException("El nombre del archivo es inválido");
        }

        if (FileUtils.hasNoExtension(filename)) {
            throw new FileProcessorException("El archivo no tiene una extensión válida");
        }

        if (!this.expectedFileType().getExtension().equalsIgnoreCase(FileUtils.getFileExtension(multipartFile))) {
            throw new FileProcessorException("Se esperaba un archivo con extensión: " + this.expectedFileType().getExtension());
        }
    }

    protected void generateProcessingReport(ProcessResult result, MultipartFile multipartFile) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = LocalDateTime.now().format(formatter);

        String extension = FileUtils.getFileExtension(multipartFile);
        String status = this.determineProcessingStatus(result);

        StringBuilder report = new StringBuilder("=== REPORTE DE PROCESAMIENTO %s ===".formatted(extension));
        report.append("\n");
        report.append("Archivo Original: ").append(multipartFile.getOriginalFilename()).append("\n");
        report.append("Archivo Almacenado: ").append(result.sourceInfo().filename()).append("\n");
        report.append("Ubicación: ").append(result.sourceInfo().location()).append("\n");
        report.append("Storage Type: ").append(result.sourceInfo().storageType()).append("\n");
        report.append("Batch ID: ").append(result.batchId()).append("\n");
        report.append("Fecha/Hora: ").append(timestamp).append("\n");
        report.append("Processor: ").append(this.getClass().getSimpleName()).append("\n");
        report.append("Total registros: ").append(result.totalRecords()).append("\n");
        report.append("Registros procesados: ").append(result.processedRecords()).append("\n");
        report.append("Registros inválidos: ").append(result.invalidRecords()).append("\n");
        report.append("Estado: ").append(status);

        log.info("REPORTE {}:\n{}", extension, report);
    }

    // ============== MÉTODOS ABSTRACTOS (específicos por tipo de archivo) ==============
    public abstract FileType expectedFileType();

    protected abstract ValidationResult validateFileStructure(Path path);

    protected abstract List<T> parseFileContent(Path path);

    protected abstract BusinessValidationResult<T> validateBusinessRules(List<T> parsedRecords);

    protected abstract int storeRecords(List<T> validRecords, String batchId);

    // ============== MÉTODOS PRIVADOS ==============
    private String determineProcessingStatus(ProcessResult result) {
        if (!result.success()) {
            return "Error";
        } else if (result.invalidRecords() > 0) {
            return "Exitoso con advertencias";
        } else {
            return "Exitoso";
        }
    }
}
