package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto;

public interface PaymentRequest {
    String orderId();

    double amount();

    String currency();
}
