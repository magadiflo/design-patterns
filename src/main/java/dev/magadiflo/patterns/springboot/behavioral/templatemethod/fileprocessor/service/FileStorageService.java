package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.FileStorageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileStorageService {
    FileStorageInfo storeFile(MultipartFile multipartFile, String batchId);

    InputStream retrieveFileContent(FileStorageInfo info);
}
