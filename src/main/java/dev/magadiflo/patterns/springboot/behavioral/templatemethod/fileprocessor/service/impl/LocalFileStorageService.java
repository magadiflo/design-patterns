package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.impl;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.StorageType;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.exception.FileProcessorException;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.FileStorageInfo;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.FileStorageService;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@Service
public class LocalFileStorageService implements FileStorageService {

    @Override
    public FileStorageInfo storeFile(MultipartFile multipartFile, String batchId) {
        try {
            Path storagePath = Path.of("M:", "temp_files");
            Files.createDirectories(storagePath);

            String extension = FileUtils.getFileExtension(multipartFile);
            String filename = FileUtils.generateFilename(extension, batchId);

            Path fullPath = storagePath.resolve(filename);
            Files.copy(multipartFile.getInputStream(), fullPath, StandardCopyOption.REPLACE_EXISTING);

            return FileStorageInfo.create(fullPath.toString(), filename, StorageType.LOCAL);
        } catch (IOException e) {
            log.error("Error al almacenar el archivo en local: {}", e.getMessage(), e);
            throw new FileProcessorException("Error al almacenar el archivo en local: %s".formatted(e.getMessage()));
        }
    }

    @Override
    public InputStream retrieveFileContent(FileStorageInfo info) {
        try {
            Path path = Path.of(info.location());
            return Files.newInputStream(path);
        } catch (IOException e) {
            log.error("No se pudo leer archivo local: {}", e.getMessage(), e);
            throw new FileProcessorException("No se pudo leer archivo local: " + e.getMessage());
        }
    }
}
