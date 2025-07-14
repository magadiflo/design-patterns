package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.factory;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.strategy.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentStrategyFactory {

    private final Map<String, PaymentStrategy<? extends PaymentRequest>> paymentStrategies;

    @SuppressWarnings("unchecked")
    public <T extends PaymentRequest> PaymentStrategy<T> getPaymentStrategy(String paymentType) {
        PaymentStrategy<? extends PaymentRequest> strategy = this.paymentStrategies.get(paymentType);
        if (Objects.isNull(strategy)) {
            throw new IllegalArgumentException("Estrategia no soportada: " + paymentType);
        }
        return (PaymentStrategy<T>) strategy;
    }

    public List<String> getSupportedPaymentTypes() {
        return this.paymentStrategies.keySet().stream().sorted().toList();
    }
}
