package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto;

public record CreditCardPaymentRequest(String orderId,
                                       double amount,
                                       String currency,
                                       String cardNumber,
                                       String expiryDate,
                                       String cvv) implements PaymentRequest {

}
