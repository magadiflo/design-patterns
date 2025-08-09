package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.entity.Product;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.InsufficientStockException;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.ProductNotFoundException;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class StockValidationHandler extends BaseOrderValidationHandler {

    private final ProductService productService;

    @Override
    protected void doHandle(PurchaseOrder order) {
        log.info("[StockValidation] Verificando disponibilidad de productos");
        order.items().forEach(orderItem -> {
            Product product = this.productService.findById(orderItem.productId());
            if (Objects.isNull(product)) {
                throw new ProductNotFoundException(orderItem.productId());
            }

            if (orderItem.quantity() > product.getAvailableStock()) {
                throw new InsufficientStockException(product.getName(), product.getAvailableStock(), orderItem.quantity());
            }
            log.info("[StockValidation] Stock OK para {} (Disponible: {}, Solicitado: {})",
                    product.getName(), product.getAvailableStock(), orderItem.quantity());
        });
    }
}
