package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception;

public class CustomerInactiveException extends BusinessValidationException {
    public CustomerInactiveException(Long customerId) {
        super("Cliente con ID %d está inactivo".formatted(customerId));
    }
}
