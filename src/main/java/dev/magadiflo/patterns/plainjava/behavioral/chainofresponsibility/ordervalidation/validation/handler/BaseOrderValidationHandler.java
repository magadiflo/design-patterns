package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.handler;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto.ValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.PurchaseOrder;

import java.util.Objects;

public abstract class BaseOrderValidationHandler implements OrderValidationHandler {

    protected OrderValidationHandler nextHandler;

    @Override
    public OrderValidationHandler setNext(OrderValidationHandler handler) {
        this.nextHandler = handler;
        return handler; // Permite el encadenamiento: handler1.setNext(handler2).setNext(handler3)
    }

    @Override
    public final ValidationResult handle(PurchaseOrder order) {
        // Ejecuta la validación específica del ConcreteHandler
        ValidationResult result = this.doHandle(order);

        // Terminación anticipada: si falla, cortar la cadena inmediatamente
        if (!result.isValid()) {
            return result;
        }

        // Si hay un siguiente handler y la validación actual fue exitosa, continúe la cadena
        if (Objects.nonNull(this.nextHandler)) {
            return this.nextHandler.handle(order);
        }

        // Si no hay más handlers y llegamos hasta aquí, everything es válido
        return ValidationResult.success();
    }

    /**
     * Method abstracto que los ConcreteHandlers deben implementar
     * Aquí es donde se aplica la lógica específica de cada validación
     */
    protected abstract ValidationResult doHandle(PurchaseOrder order);
}
