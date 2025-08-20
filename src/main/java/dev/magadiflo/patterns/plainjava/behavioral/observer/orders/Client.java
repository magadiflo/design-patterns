package dev.magadiflo.patterns.plainjava.behavioral.observer.orders;

import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.event.OrderStatus;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.publisher.OrderPublisher;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber.*;
import dev.magadiflo.patterns.plainjava.behavioral.observer.orders.subscriber.SalesMetricsOrderSubscriber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Client {
    public static void main(String[] args) {
        // Crear el Publisher (Subject)
        OrderPublisher orderPublisher = new OrderPublisher();

        // Crear los Subscribers (Observers)
        AccountingOrderSubscriber accountingSubscriber = new AccountingOrderSubscriber();
        EmailNotificationOrderSubscriber emailSubscriber = new EmailNotificationOrderSubscriber();
        InventoryOrderSubscriber inventorySubscriber = new InventoryOrderSubscriber();
        InvoiceGeneratorOrderSubscriber invoiceSubscriber = new InvoiceGeneratorOrderSubscriber();
        SalesMetricsOrderSubscriber metricsSubscriber = new SalesMetricsOrderSubscriber();
        SMSNotificationOrderSubscriber smsSubscriber = new SMSNotificationOrderSubscriber();

        // Subscribir observers al publisher
        orderPublisher.subscribe(accountingSubscriber);
        orderPublisher.subscribe(emailSubscriber);
        orderPublisher.subscribe(inventorySubscriber);
        orderPublisher.subscribe(invoiceSubscriber);
        orderPublisher.subscribe(metricsSubscriber);
        orderPublisher.subscribe(smsSubscriber);

        // Escenario 01: Pedido confirmado
        log.info("=== Pedido CONFIRMADO ===");
        orderPublisher.changeOrderStatus("ORD-001", OrderStatus.CONFIRMED);

        // Escenario 02: Pedido enviado
        log.info("=== Pedido ENVIADO ===");
        orderPublisher.changeOrderStatus("ORD-002", OrderStatus.SHIPPED);

        // Escenario 03: Pedido cancelado
        log.info("=== Pedido CANCELADO ===");
        orderPublisher.changeOrderStatus("ORD-003", OrderStatus.CANCELLED);
    }
}
