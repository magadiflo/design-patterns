package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.util;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.exception.FileProcessorException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public class FileUtils {

    public static Path retrieveAsTempFile(InputStream inputStream, String filename) {
        try (InputStream is = inputStream) {
            Path tempFile = Files.createTempFile("temp_", "_" + filename);
            Files.copy(is, tempFile, StandardCopyOption.REPLACE_EXISTING);
            return tempFile;
        } catch (IOException e) {
            throw new FileProcessorException("Error al recuperar archivo temporal: " + e.getMessage());
        }
    }

    public static void deleteTempFile(Path path) {
        if (Objects.isNull(path)) return;
        try {
            if (Files.exists(path)) {
                Files.delete(path);
                log.debug("Archivo temporal eliminado: {}", path);
            }
        } catch (IOException e) {
            log.warn("No se pudo eliminar archivo temporal: {}", path, e);
        }
    }

    public static String generateFilename(String extension, String batchId) {
        return "%s_%d.%s".formatted(batchId, System.currentTimeMillis(), extension);
    }

    public static String getFileExtension(MultipartFile multipartFile) {
        String filename = multipartFile.getOriginalFilename();
        if (Objects.isNull(filename) || filename.isBlank() || hasNoExtension(filename)) {
            throw new FileProcessorException("El archivo no tiene un nombre o extensión válida");
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public static boolean hasNoExtension(String filename) {
        return !Pattern.matches(".+\\.[a-zA-Z0-9]+", filename);
    }
}
