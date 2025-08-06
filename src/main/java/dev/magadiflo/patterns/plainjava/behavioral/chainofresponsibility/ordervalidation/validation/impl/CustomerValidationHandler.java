package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.impl;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto.ValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.Customer;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.PurchaseOrder;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service.CustomerService;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.handler.BaseOrderValidationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class CustomerValidationHandler extends BaseOrderValidationHandler {

    private final CustomerService customerService;

    @Override
    protected ValidationResult doHandle(PurchaseOrder order) {
        log.info("[Validador de clientes] Verificando cliente con ID: {}", order.customerId());

        Customer customer = this.customerService.findById(order.customerId());

        if (Objects.isNull(customer)) {
            return ValidationResult.failure(
                    "Cliente no encontrado con ID: " + order.customerId(),
                    "CUSTOMER_NOT_FOUND"
            );
        }

        if (!customer.active()) {
            return ValidationResult.failure("Cliente inactivo", "CUSTOMER_INACTIVE");
        }

        log.info("Cliente válido: {}", customer.name());
        return ValidationResult.success();
    }
}
