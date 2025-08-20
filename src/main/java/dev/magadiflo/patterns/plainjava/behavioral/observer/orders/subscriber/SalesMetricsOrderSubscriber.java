package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderEvent;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SalesMetricsOrderSubscriber implements OrderSubscriber {
    @Override
    public void update(OrderEvent orderEvent) {
        switch (orderEvent.status()) {
            case OrderStatus.CONFIRMED -> log.info("Métricas: Venta confirmada registrada - {}", orderEvent.orderId());
            case OrderStatus.CANCELLED -> log.info("Métricas: Venta cancelada registrada - {}", orderEvent.orderId());
        }
    }
}
