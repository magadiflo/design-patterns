package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.controller;

import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.enums.FileType;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model.ProcessResult;
import dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.service.FileProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/files")
public class FileProcessorController {

    private final FileProcessingService fileProcessingService;

    @PostMapping(path = "/process")
    public ResponseEntity<ProcessResult> processFile(@RequestParam("file") MultipartFile multipartFile,
                                                     @RequestParam(required = false, defaultValue = "") String batchId) {
        if (batchId.isBlank()) {
            batchId = "BATCH_" + System.currentTimeMillis();
            log.info("BatchId generado automáticamente: {}", batchId);
        }

        ProcessResult result = this.fileProcessingService.processFile(multipartFile, batchId);
        HttpStatus status = result.success() ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY;
        log.info("Procesamiento finalizado - Status: {}, ¿Éxito?: {}", status, result.success());
        return ResponseEntity.status(status).body(result);
    }

    @GetMapping(path = "/supported-types")
    public ResponseEntity<Set<FileType>> getSupportedFileTypes() {
        Set<FileType> supportedTypes = this.fileProcessingService.getSupportedFileTypes();
        log.debug("Tipos de archivos soportados: {}", supportedTypes);
        return ResponseEntity.ok(supportedTypes);
    }

}
