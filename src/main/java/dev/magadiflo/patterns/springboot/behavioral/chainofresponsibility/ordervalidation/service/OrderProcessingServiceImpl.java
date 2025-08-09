package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.response.OrderResponse;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler.OrderValidationHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderProcessingServiceImpl implements OrderProcessingService {

    private final OrderValidationHandler orderValidationChain;

    public OrderProcessingServiceImpl(@Qualifier(value = "orderValidationChain")
                                      OrderValidationHandler orderValidationChain) {
        this.orderValidationChain = orderValidationChain;
        log.info("Cadena de validación inyectada: {}", orderValidationChain.getClass().getSimpleName());
    }

    @Override
    public OrderResponse processOrder(PurchaseOrder order) {
        log.info("==== Procesando orden ====");
        log.info("Customer ID: {}", order.customerId());
        log.info("Items: {}", order.items().size());
        log.info("Total amount: $ {}", order.getTotalAmount());
        log.info("Destination: {}", order.shippingAddress().city());
        log.info("===========================");

        // Ejecutar cadena de validación - las excepciones se propagan automáticamente
        this.orderValidationChain.handle(order);

        // Si llegamos aquí, todas las validaciones pasaron
        log.info("Orden procesada correctamente");
        log.info("El pedido ha sido registrado y está listo para su preparación");

        // Aquí iría la lógica de persistencia real
        // PurchaseOrderEntity entity = orderMapper.toEntity(order);
        // orderRepository.save(entity);
        // notificationService.sendOrderConfirmation(order);
        // inventoryService.reserveStock(order.items());
        return new OrderResponse(
                this.generateOrderId(),
                "ORDER_CREATED",
                "Orden creada exitosamente",
                order.getTotalAmount()
        );
    }

    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis();
    }
}
