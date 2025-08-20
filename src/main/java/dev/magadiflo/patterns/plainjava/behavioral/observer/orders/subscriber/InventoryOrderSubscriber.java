package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderEvent;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InventoryOrderSubscriber implements OrderSubscriber {
    @Override
    public void update(OrderEvent orderEvent) {
        switch (orderEvent.status()) {
            case OrderStatus.CONFIRMED -> log.info("Inventario: Stock reducido para pedido {}", orderEvent.orderId());
            case OrderStatus.CANCELLED -> log.info("Inventario: Stock restaurado para pedido {}", orderEvent.orderId());
            // SHIPPED no afecta el inventario
        }
    }
}
