package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception;

public class InvalidShippingAddressException extends BusinessValidationException {
    public InvalidShippingAddressException() {
        super("Dirección de envío no válida. Verifique el formato de los campos y el país de destino.");
    }
}
