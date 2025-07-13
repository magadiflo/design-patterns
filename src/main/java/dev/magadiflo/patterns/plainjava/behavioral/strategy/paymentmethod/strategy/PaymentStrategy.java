package dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy;

import java.io.BufferedReader;

public interface PaymentStrategy {
    void collectPaymentDetails(BufferedReader reader);

    boolean validatePaymentDetails();

    boolean pay(int amount);
}
