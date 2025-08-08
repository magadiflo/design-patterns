package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception;

public class CustomerNotFoundException extends BusinessValidationException {
    public CustomerNotFoundException(Long customerId) {
        super("Cliente no encontrado con ID: %d".formatted(customerId));
    }
}
