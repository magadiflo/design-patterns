package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderEvent;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvoiceGeneratorOrderSubscriber implements OrderSubscriber {
    @Override
    public void update(OrderEvent orderEvent) {
        if (orderEvent.status() == OrderStatus.CONFIRMED) {
            log.info("Factura: PDF generado para pedido {} -> INV-{}.pdf", orderEvent.orderId(), orderEvent.orderId());
        }
    }
}
