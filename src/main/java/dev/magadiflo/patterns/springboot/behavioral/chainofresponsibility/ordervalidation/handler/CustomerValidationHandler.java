package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.entity.Customer;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.CustomerInactiveException;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.CustomerNotFoundException;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomerValidationHandler extends BaseOrderValidationHandler {

    private final CustomerService customerService;

    @Override
    protected void doHandle(PurchaseOrder order) {
        log.info("[CustomerValidation] Verificando cliente con ID: {}", order.customerId());

        Long customerId = order.customerId();
        Customer customer = this.customerService.findById(customerId);
        if (Objects.isNull(customer)) {
            throw new CustomerNotFoundException(customerId);
        }

        if (Boolean.FALSE.equals(customer.getActive())) {
            throw new CustomerInactiveException(customerId);
        }

        log.info("[CustomerValidation] Cliente válido: {}", customer.getName());
    }
}
