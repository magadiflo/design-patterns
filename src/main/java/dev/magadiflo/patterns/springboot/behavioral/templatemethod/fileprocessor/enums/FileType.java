package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum FileType {

    TXT("txt", "text/plain"),
    CSV("csv", "text/csv"),
    JSON("json", "application/json"),
    XML("xml", "application/xml");

    private final String extension;
    private final String mimeType;

    FileType(String extension, String mimeType) {
        this.extension = extension;
        this.mimeType = mimeType;
    }

    public static Optional<FileType> fromExtension(String extension) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getExtension().equalsIgnoreCase(extension))
                .findFirst();
    }

    public static Optional<FileType> fromMimeType(String mimeType) {
        return Arrays.stream(FileType.values())
                .filter(fileType -> fileType.getMimeType().equalsIgnoreCase(mimeType))
                .findFirst();
    }

}
