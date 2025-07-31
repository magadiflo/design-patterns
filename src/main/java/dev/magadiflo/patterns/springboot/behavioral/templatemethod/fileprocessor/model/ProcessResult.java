package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProcessResult(String batchId,
                            boolean success,
                            String errorMessage,
                            int totalRecords,
                            int processedRecords,
                            int invalidRecords,
                            FileStorageInfo sourceInfo) {

    public static ProcessResult success(String batchId, int total, int processed, int invalid, FileStorageInfo sourceInfo) {
        return new ProcessResult(batchId, true, null, total, processed, invalid, sourceInfo);
    }

    public static ProcessResult failure(String batchId, String errorMessage, FileStorageInfo sourceInfo) {
        return new ProcessResult(batchId, false, errorMessage, 0, 0, 0, sourceInfo);
    }
}
