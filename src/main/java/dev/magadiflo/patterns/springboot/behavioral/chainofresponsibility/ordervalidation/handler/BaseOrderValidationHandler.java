package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;

import java.util.Objects;

public abstract class BaseOrderValidationHandler implements OrderValidationHandler {

    protected OrderValidationHandler nextHandler;

    @Override
    public OrderValidationHandler setNext(OrderValidationHandler handler) {
        this.nextHandler = handler;
        return handler;
    }

    @Override
    public final void handle(PurchaseOrder order) {
        // Ejecuta validación específica, si falla, lanza excepción
        this.doHandle(order);

        // Si llegamos aquí, la validación fue exitosa, continuamos con la cadena
        if (Objects.nonNull(this.nextHandler)) {
            this.nextHandler.handle(order);
        }
    }

    protected abstract void doHandle(PurchaseOrder order);
}
