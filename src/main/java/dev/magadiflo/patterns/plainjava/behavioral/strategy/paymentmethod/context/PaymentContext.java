package dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.context;

import dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.util.Objects;

@Slf4j
public class PaymentContext {

    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void collectPaymentDetails(BufferedReader reader) {
        this.validateStrategy();
        this.paymentStrategy.collectPaymentDetails(reader);
    }

    public boolean validatePaymentDetails() {
        this.validateStrategy();
        return this.paymentStrategy.validatePaymentDetails();
    }

    public boolean pay(int amount) {
        this.validateStrategy();
        return this.paymentStrategy.pay(amount);
    }

    private void validateStrategy() {
        if (Objects.isNull(this.paymentStrategy)) {
            throw new IllegalStateException("No se ha definido una estrategia de pago");
        }
    }
}
