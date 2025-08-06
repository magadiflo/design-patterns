package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.handler;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto.ValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.PurchaseOrder;

public interface OrderValidationHandler {
    // Method para establecer el siguiente handler en la cadena
    OrderValidationHandler setNext(OrderValidationHandler handler);

    // Method principal para manejar solicitudes
    ValidationResult handle(PurchaseOrder order);
}
