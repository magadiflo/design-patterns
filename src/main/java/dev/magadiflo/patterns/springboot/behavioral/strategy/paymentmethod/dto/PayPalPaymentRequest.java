package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto;

public record PayPalPaymentRequest(String orderId,
                                   double amount,
                                   String currency,
                                   String paypalEmail,
                                   String password) implements PaymentRequest {
}
