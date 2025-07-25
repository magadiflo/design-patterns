package dev.magadiflo.patterns.springboot.behavioral.templatemethod.fileprocessor.model;

public record FileTypeValidationResult(boolean isValid,
                                       String errorMessage,
                                       String detectedType,
                                       String expectedType) {

    public static FileTypeValidationResult success(String detectedType, String expectedType) {
        return new FileTypeValidationResult(true, null, detectedType, expectedType);
    }

    public static FileTypeValidationResult failure(String errorMessage,
                                                   String detectedType,
                                                   String expectedType) {
        return new FileTypeValidationResult(false, errorMessage, detectedType, expectedType);
    }

    public static FileTypeValidationResult failure(String errorMessage) {
        return new FileTypeValidationResult(false, errorMessage, null, null);
    }
}
