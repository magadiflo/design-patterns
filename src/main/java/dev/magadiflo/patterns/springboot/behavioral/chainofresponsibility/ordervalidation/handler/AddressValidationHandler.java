package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.InvalidShippingAddressException;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class AddressValidationHandler extends BaseOrderValidationHandler {

    private final AddressService addressService;

    @Override
    protected void doHandle(PurchaseOrder order) {
        log.info("[AddressValidation] Verificando: {}, {}", order.shippingAddress().city(), order.shippingAddress().country());
        if (!this.addressService.isValidAddress(order.shippingAddress())) {
            throw new InvalidShippingAddressException();
        }
        log.info("[AddressValidation] Dirección válida para el envío");
    }
}
