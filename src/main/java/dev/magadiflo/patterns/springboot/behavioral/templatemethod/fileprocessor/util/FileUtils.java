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
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Pattern;

@Slf4j
@UtilityClass
public class FileUtils {

    public static Path retrieveAsTempFile(InputStream inputStream, String filename) {
        try (InputStream is = inputStream) {
            Path tempDir = Files.createTempDirectory("file_processor");
            Path fullPathTempFile = tempDir.resolve(filename);
            Files.copy(is, fullPathTempFile, StandardCopyOption.REPLACE_EXISTING);
            return fullPathTempFile;
        } catch (IOException e) {
            throw new FileProcessorException("Error al recuperar archivo temporal: " + e.getMessage());
        }
    }

    public static void deleteTempFile(Path path) {
        if (Objects.isNull(path)) {
            log.warn("El path es null. No hay archivo temporal qué eliminar");
            return;
        }

        Path parentTempDir = path.getParent();
        if (Objects.isNull(parentTempDir) || !Files.exists(parentTempDir)) {
            log.warn("El directorio temporal no existe: {}", parentTempDir);
            return;
        }

        try {
            Files.walk(parentTempDir)
                    .sorted(Comparator.reverseOrder()) // Elimina primero los archivos, luego los directorios
                    .forEach(pathTemp -> {
                        try {
                            Files.delete(pathTemp);
                            log.info("Eliminado: {}", pathTemp);
                        } catch (IOException e) {
                            log.warn("No se pudo eliminar: {}", pathTemp, e);
                        }
                    });
        } catch (IOException e) {
            log.warn("Error al recorrer/eliminar el directorio temporal: {}", parentTempDir, e);
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
