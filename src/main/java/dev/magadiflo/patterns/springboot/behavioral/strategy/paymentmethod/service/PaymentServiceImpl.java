package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.service;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.converter.PaymentRequestConverter;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentResponse;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.RawPaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.factory.PaymentStrategyFactory;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentStrategyFactory strategyFactory;
    private final PaymentRequestConverter requestConverter;

    @Override
    public PaymentResponse processPayment(RawPaymentRequest rawPaymentRequest) {
        PaymentRequest paymentRequest = this.requestConverter.createPaymentRequest(rawPaymentRequest);
        return this.applyPaymentStrategy(rawPaymentRequest.paymentType(), paymentRequest);
    }

    @Override
    public List<String> getSupportedPaymentTypes() {
        return this.strategyFactory.getSupportedPaymentTypes();
    }

    private <T extends PaymentRequest> PaymentResponse applyPaymentStrategy(String paymentType, T request) {
        PaymentStrategy<T> strategy = this.strategyFactory.getPaymentStrategy(paymentType);
        log.info("Estrategia obtenida {}", strategy.getClass().getSimpleName());

        if (!strategy.validatePayment(request)) {
            log.error("Error al validar el pago");
            throw new IllegalArgumentException("No se pudo procesar el pago");
        }

        return strategy.pay(request);
    }
}
