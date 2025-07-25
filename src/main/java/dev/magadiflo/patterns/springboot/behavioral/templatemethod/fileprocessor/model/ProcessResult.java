package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model;

public record ProcessResult(String batchId,
                            boolean success,
                            String errorMessage,
                            int totalRecords,
                            int processedRecords,
                            int invalidRecords) {

    public static ProcessResult success(String batchId, int total, int processed, int invalid) {
        return new ProcessResult(batchId, true, null, total, processed, invalid);
    }

    public static ProcessResult failure(String batchId, String errorMessage) {
        return new ProcessResult(batchId, false, errorMessage, 0, 0, 0);
    }
}
