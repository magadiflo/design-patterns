package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto;

public record BankTransferPaymentRequest(String orderId,
                                         double amount,
                                         String currency,
                                         String accountHolder,
                                         String bankName,
                                         String accountNumber) implements PaymentRequest {
}
