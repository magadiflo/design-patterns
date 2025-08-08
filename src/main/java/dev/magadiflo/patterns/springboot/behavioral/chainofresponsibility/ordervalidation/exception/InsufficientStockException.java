package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception;

public class InsufficientStockException extends BusinessValidationException {
    public InsufficientStockException(String productName, int available, int requested) {
        super("Stock insuficiente para %s. Disponible: %d, Solicitado: %d".formatted(productName, available, requested));
    }
}
