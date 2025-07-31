package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.impl;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.FileType;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.exception.FileProcessorException;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.factory.FileProcessorFactory;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ParsedRecord;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ProcessResult;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.processor.FileProcessor;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.FileProcessingService;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.util.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileProcessingServiceImpl implements FileProcessingService {

    private final FileProcessorFactory fileProcessorFactory;

    @Override
    public ProcessResult processFile(MultipartFile multipartFile, String batchId) {
        // Paso 1: Detectar el tipo de archivo
        FileType fileType = this.detectFileType(multipartFile);
        log.info("Tipo de archivo detectado: {}", fileType);

        // Paso 2: Validar que el tipo sea soportado
        this.validateSupportedFileType(fileType);

        // Paso 3: Obtener el processor apropiado
        FileProcessor<? extends ParsedRecord> processor = this.fileProcessorFactory.getProcessor(fileType);
        log.info("Processor obtenido: {}", processor.getClass().getSimpleName());

        // Paso 4: Ejecutar procesamiento
        ProcessResult result = processor.processFile(multipartFile, batchId);
        log.info("Procesamiento completado. ¿Éxito?: {}, registros procesados: {}", result.success(), result.processedRecords());

        return result;
    }

    @Override
    public Set<FileType> getSupportedFileTypes() {
        return this.fileProcessorFactory.getSupportedFileTypes();
    }

    private FileType detectFileType(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        String mimeType = multipartFile.getContentType();

        log.debug("Detectando tipo - MIME: {}, Filename: {}", mimeType, filename);

        // Primero intentamos por el tipo mime
        if (Objects.nonNull(mimeType) && !mimeType.isBlank()) {
            Optional<FileType> fileTypeByMime = FileType.fromMimeType(mimeType);
            if (fileTypeByMime.isPresent()) {
                log.debug("Tipo detectado por MIME type: {}", fileTypeByMime.get());
                return fileTypeByMime.get();
            }
        }

        // Si no funciona MIME, intentamos por extensión
        if (Objects.nonNull(filename) && !filename.isBlank()) {
            String extension = FileUtils.getFileExtension(multipartFile);
            Optional<FileType> fileTypeByExtension = FileType.fromExtension(extension);
            if (fileTypeByExtension.isPresent()) {
                log.debug("Tipo detectado por extensión: {}", fileTypeByExtension.get());
                return fileTypeByExtension.get();
            }
        }

        throw new FileProcessorException("No se pudo determinar el tipo de archivo. MIME: %s, Filename: %s".
                formatted(mimeType, filename));
    }

    private void validateSupportedFileType(FileType fileType) {
        Set<FileType> supportedFileTypes = this.fileProcessorFactory.getSupportedFileTypes();
        if (!supportedFileTypes.contains(fileType)) {
            throw new FileProcessorException("Tipo de archivo no soportado: %s. Tipos soportados: %s"
                    .formatted(fileType, supportedFileTypes));
        }
        log.debug("Tipo de archivo {} es soportado", fileType);
    }
}
