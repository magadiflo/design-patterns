package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.StorageType;

public record FileStorageInfo(String location,
                              String filename,
                              StorageType storageType) {

    public static FileStorageInfo create(String location, String filename, StorageType storageType) {
        return new FileStorageInfo(location, filename, storageType);
    }
}
