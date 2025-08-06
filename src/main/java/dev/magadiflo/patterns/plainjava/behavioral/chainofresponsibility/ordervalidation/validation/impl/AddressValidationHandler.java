package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.impl;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto.ValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.PurchaseOrder;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.ShippingAddress;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service.AddressService;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.handler.BaseOrderValidationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class AddressValidationHandler extends BaseOrderValidationHandler {

    private final AddressService addressService;

    @Override
    protected ValidationResult doHandle(PurchaseOrder order) {
        ShippingAddress address = order.shippingAddress();
        log.info("[Validador de dirección] verificando: {}, {}", address.city(), address.country());

        if (!this.addressService.isValidAddress(address)) {
            return ValidationResult.failure(
                    "Dirección de envío no válida. Verifique el formato de los campos y el país de destino.",
                    "INVALID_ADDRESS");
        }
        log.info("Dirección válido para el envío");
        return ValidationResult.success();
    }
}
