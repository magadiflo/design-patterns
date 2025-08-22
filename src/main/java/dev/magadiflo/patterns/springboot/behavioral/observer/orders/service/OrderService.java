package dev.magadiflo.patterns.springboot.behavioral.observer.orders.service;

import dev.magadiflo.patterns.springboot.behavioral.observer.orders.event.OrderStatusChangedEvent;
import dev.magadiflo.patterns.springboot.behavioral.observer.orders.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ApplicationEventPublisher eventPublisher;

    public void changeOrderStatus(String orderId, OrderStatus newStatus, String correlationId) {
        // Aquí iría la lógica transaccional real (persistir estado, etc.)
        log.info("Cambio de estado [pedido: {} -> {}] (corrId={})", orderId, newStatus, correlationId);

        // Publicamos el evento de dominio
        OrderStatusChangedEvent event = OrderStatusChangedEvent.of(orderId, newStatus, correlationId);
        log.info("Publicando evento: {} para pedido {}", newStatus, orderId);
        this.eventPublisher.publishEvent(event);
    }
}
