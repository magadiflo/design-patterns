package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.client.OrderProcessingClient;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto.ValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.OrderItem;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.PurchaseOrder;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.ShippingAddress;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Main {
    public static void main(String[] args) {
        // CASO 1: Pedido completamente válido
        // Creación de elementos de la orden
        List<OrderItem> items = List.of(new OrderItem(101L, 1, 1200.00), new OrderItem(103L, 1, 89.90));
        ShippingAddress address = new ShippingAddress("Av. universitaria", "Nuevo Chimbote", "10001", "USA");
        PurchaseOrder order = new PurchaseOrder(1L, items, address);

        // Cliente que inicia la validación
        OrderProcessingClient client = new OrderProcessingClient();
        ValidationResult result = client.processOrder(order);

        // Resultado global de la validación
        log.info("Resultado final: {}", result.isValid());

        log.info("---------------------------------------------------------------------------------------------------");

        // CASO 2: Stock insuficiente (falla en la segunda validación)
        List<OrderItem> items2 = List.of(new OrderItem(102L, 5, 25.50));
        ShippingAddress address2 = new ShippingAddress("Av. universitaria", "Nuevo Chimbote", "10001", "USA");
        PurchaseOrder order2 = new PurchaseOrder(1L, items2, address2);
        OrderProcessingClient client2 = new OrderProcessingClient();
        ValidationResult result2 = client2.processOrder(order2);
        log.info("Resultado final 2: {}", result2.isValid());
    }
}
