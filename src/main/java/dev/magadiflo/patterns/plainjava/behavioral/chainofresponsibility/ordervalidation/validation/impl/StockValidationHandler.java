package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.impl;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto.ValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.OrderItem;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.Product;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.PurchaseOrder;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service.ProductService;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.handler.BaseOrderValidationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class StockValidationHandler extends BaseOrderValidationHandler {

    private final ProductService productService;

    @Override
    protected ValidationResult doHandle(PurchaseOrder order) {
        log.info("[Validador de stock] Verificando disponibilidad de producto");

        for (OrderItem orderItem : order.items()) {
            Product product = this.productService.findById(orderItem.productId());

            if (Objects.isNull(product)) {
                return ValidationResult.failure("Producto no encontrado con id: " + orderItem.productId(), "PRODUCT_NOT_FOUND");
            }

            if (orderItem.quantity() > product.availableStock()) {
                return ValidationResult.failure(
                        "Stock insuficiente para %s. Disponible: %d, Solicitado: %d"
                                .formatted(product.name(), product.availableStock(), orderItem.quantity()),
                        "INSUFFICIENT_STOCK");
            }
            log.info("Stock OK para {} (Disponible {}, solicitado {})", product.name(), product.availableStock(), orderItem.quantity());
        }

        return ValidationResult.success();
    }
}
