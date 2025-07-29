package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.controller;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.TxtRecord;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.FileProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/file-processor")
public class FileProcessorController {

    private final FileProcessor<TxtRecord> fileProcessor;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> processFile(@RequestParam("file") MultipartFile multipartFile) {
        this.fileProcessor.processFile(multipartFile, "BATCH_TXT");
        return ResponseEntity.noContent().build();
    }

}
