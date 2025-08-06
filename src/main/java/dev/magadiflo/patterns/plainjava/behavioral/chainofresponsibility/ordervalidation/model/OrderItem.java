package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model;

public record OrderItem(Long productId,
                        int quantity,
                        double unitPrice) {
    public double getSubTotal() {
        return this.quantity * this.unitPrice;
    }
}
