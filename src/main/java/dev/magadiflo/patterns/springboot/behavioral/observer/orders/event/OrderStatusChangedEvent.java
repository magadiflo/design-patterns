package dev.magadiflo.patterns.springboot.behavioral.observer.orders.event;

import dev.magadiflo.patterns.springboot.behavioral.observer.orders.model.OrderStatus;

import java.time.Instant;

public record OrderStatusChangedEvent(String orderId,
                                      OrderStatus status,
                                      Instant occurredAt,
                                      String correlationId) {

    public static OrderStatusChangedEvent of(String orderId, OrderStatus status, String correlationId) {
        return new OrderStatusChangedEvent(orderId, status, Instant.now(), correlationId);
    }
}
