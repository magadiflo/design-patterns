package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.FileType;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ProcessResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface FileProcessingService {
    ProcessResult processFile(MultipartFile multipartFile, String batchId);

    Set<FileType> getSupportedFileTypes();
}
