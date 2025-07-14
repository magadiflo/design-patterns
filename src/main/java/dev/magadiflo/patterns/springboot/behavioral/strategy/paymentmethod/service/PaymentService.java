package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.service;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentResponse;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.RawPaymentRequest;

import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(RawPaymentRequest rawPaymentRequest);

    List<String> getSupportedPaymentTypes();
}
