package dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event;

// Clase que representa el evento (estado del pedido + datos básicos).
public record OrderEvent(String orderId, OrderStatus status) {
}
