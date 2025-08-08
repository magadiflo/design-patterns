package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception;

public class ProductNotFoundException extends BusinessValidationException {
    public ProductNotFoundException(Long productId) {
        super("Producto no encontrado con ID: %d".formatted(productId));
    }
}
