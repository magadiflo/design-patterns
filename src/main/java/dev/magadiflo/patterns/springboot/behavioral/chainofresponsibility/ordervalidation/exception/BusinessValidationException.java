package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception;

public class BusinessValidationException extends RuntimeException {
    public BusinessValidationException(String message) {
        super(message);
    }
}
