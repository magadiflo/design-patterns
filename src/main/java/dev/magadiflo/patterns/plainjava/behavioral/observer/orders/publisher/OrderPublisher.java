package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.publisher;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderEvent;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderStatus;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber.OrderSubscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderPublisher {

    private final List<OrderSubscriber> orderSubscribers = new ArrayList<>();

    public void subscribe(OrderSubscriber orderSubscriber) {
        this.orderSubscribers.add(orderSubscriber);
        log.info("Nuevo subscriber agregado: {}", orderSubscriber.getClass().getSimpleName());
    }

    public void unsubscribe(OrderSubscriber orderSubscriber) {
        this.orderSubscribers.remove(orderSubscriber);
        log.info("Subscriber removido: {}", orderSubscriber.getClass().getSimpleName());
    }

    public void changeOrderStatus(String orderId, OrderStatus newStatus) {
        OrderEvent orderEvent = new OrderEvent(orderId, newStatus);
        log.info("Cambio de estado [Pedido: {} -> estado: {}]", orderId, newStatus);
        // Notificar a todos los suscriptores
        this.notifySubscribers(orderEvent);
    }

    private void notifySubscribers(OrderEvent orderEvent) {
        log.info("Notificando a todos los Subscribers...");
        this.orderSubscribers.forEach(subscriber -> subscriber.update(orderEvent));
        log.info("Notificación completada");
    }
}
