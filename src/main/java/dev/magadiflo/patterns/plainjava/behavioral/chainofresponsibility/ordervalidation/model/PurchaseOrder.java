package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model;

import java.util.List;

public record PurchaseOrder(Long customerId,
                            List<OrderItem> items,
                            ShippingAddress shippingAddress) {

    public double getTotalAmount() {
        return this.items.stream().mapToDouble(OrderItem::getSubTotal).sum();
    }
}
