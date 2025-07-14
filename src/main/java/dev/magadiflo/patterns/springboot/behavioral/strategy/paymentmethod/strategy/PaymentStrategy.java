package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.strategy;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentResponse;

public interface PaymentStrategy<T extends PaymentRequest> {
    boolean validatePayment(T request);

    PaymentResponse pay(T request);
}
