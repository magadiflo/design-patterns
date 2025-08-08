package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;

public interface OrderValidationHandler {
    OrderValidationHandler setNext(OrderValidationHandler handler);

    void handle(PurchaseOrder order);
}
