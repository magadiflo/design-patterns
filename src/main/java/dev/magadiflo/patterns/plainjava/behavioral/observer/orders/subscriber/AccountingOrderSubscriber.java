package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderEvent;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountingOrderSubscriber implements OrderSubscriber {
    @Override
    public void update(OrderEvent orderEvent) {
        switch (orderEvent.status()) {
            case OrderStatus.CONFIRMED -> log.info("Contabilidad: Transacción registrad para {}", orderEvent.orderId());
            case OrderStatus.CANCELLED -> log.info("Contabilidad: Reembolso procesado para {}", orderEvent.orderId());
        }
    }
}
