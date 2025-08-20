package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderEvent;

public interface OrderSubscriber {
    void update(OrderEvent orderEvent);
}
