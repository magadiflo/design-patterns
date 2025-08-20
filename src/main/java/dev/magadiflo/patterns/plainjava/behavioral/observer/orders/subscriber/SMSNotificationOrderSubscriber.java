package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderEvent;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SMSNotificationOrderSubscriber implements OrderSubscriber {
    @Override
    public void update(OrderEvent orderEvent) {
        if (orderEvent.status() == OrderStatus.SHIPPED) {
            log.info("SMS enviado: Su pedido {} fue despachado - Track: TRK{}", orderEvent.orderId(), orderEvent.orderId());
        }
    }
}
