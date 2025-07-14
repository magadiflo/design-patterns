package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto;

public record PaymentResponse(boolean success,
                              String transactionId,
                              String message) {
}
