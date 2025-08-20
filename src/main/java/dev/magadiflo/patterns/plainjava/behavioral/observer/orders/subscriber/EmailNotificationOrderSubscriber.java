package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderEvent;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailNotificationOrderSubscriber implements OrderSubscriber {
    @Override
    public void update(OrderEvent orderEvent) {
        switch (orderEvent.status()) {
            case OrderStatus.CONFIRMED -> log.info("Email enviado: Confirmación de pedido {}", orderEvent.orderId());
            case OrderStatus.SHIPPED -> log.info("Email enviado: Su pedido {} está en camino", orderEvent.orderId());
            case OrderStatus.CANCELLED -> log.info("Email enviado: Cancelación de pedido {}", orderEvent.orderId());
        }
    }
}
