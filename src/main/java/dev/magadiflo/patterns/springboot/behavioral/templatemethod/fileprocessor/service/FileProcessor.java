package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.exception.FileProcessorException;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.FileStorageInfo;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ParsedRecord;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ProcessResult;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public abstract class FileProcessor<T extends ParsedRecord> {

    private final FileStorageService fileStorageService;

    public final ProcessResult processFile(MultipartFile multipartFile, String batchId) {
        log.info("=== Iniciando procesamiento de archivo ===");
        log.info("Archivo: {} - Batch ID: {} - Tamaño: {}", multipartFile.getOriginalFilename(), batchId, multipartFile.getSize());

        // Paso 1: Validar archivo a procesar
        this.validateMultipartFile(multipartFile);
        log.info("Archivo {} validado correctamente", multipartFile.getOriginalFilename());

        // Paso 2: Almacenar el archivo y generar uno temporal
        FileStorageInfo fileStorageInfo = this.fileStorageService.storeFile(multipartFile, batchId);
        log.info("Archivo {} guardado con éxito como {}", multipartFile.getOriginalFilename(), fileStorageInfo.filename());

        InputStream inputStream = this.fileStorageService.retrieveFileContent(fileStorageInfo);
        Path pathTempFile = FileUtils.retrieveAsTempFile(inputStream, fileStorageInfo.filename());
        log.info("Procesando archivo desde: {}", pathTempFile);

        // Paso 3: Validar estructura del archivo
        log.info("Paso 3, en construcción...");

        // Paso N: Elimina archivo temporal
        FileUtils.deleteTempFile(pathTempFile);
        return null; // Retorno temporal, aún en construcción
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

        if (!this.expectedExtension().equalsIgnoreCase(FileUtils.getFileExtension(multipartFile))) {
            throw new FileProcessorException("Se esperaba un archivo con extensión: " + this.expectedExtension());
        }
    }

    // ============== MÉTODOS ABSTRACTOS (específicos por tipo de archivo) ==============
    protected abstract String expectedExtension();
}
