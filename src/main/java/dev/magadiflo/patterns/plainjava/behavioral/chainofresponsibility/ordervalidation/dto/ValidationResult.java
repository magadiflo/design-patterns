package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto;

public record ValidationResult(boolean isValid,
                               String errorMessage,
                               String errorCode) {

    public static ValidationResult success() {
        return new ValidationResult(true, null, null);
    }

    public static ValidationResult failure(String errorMessage, String errorCode) {
        return new ValidationResult(false, errorMessage, errorCode);
    }
}
