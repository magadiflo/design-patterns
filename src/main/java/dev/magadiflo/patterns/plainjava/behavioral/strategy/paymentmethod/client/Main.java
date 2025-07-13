package dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.client;

import dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.context.PaymentContext;
import dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy.BankTransferPayment;
import dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy.CreditCardPayment;
import dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy.PayPalPayment;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            PaymentContext context = new PaymentContext();

            log.info("=== Sistema de Pagos ===");
            log.info("Elija el método de pago:");
            log.info("1. PayPal");
            log.info("2. Transferencia Bancaria");
            log.info("3. Tarjeta de Crédito");

            String option = reader.readLine().trim();
            switch (option) {
                case "1" -> context.setPaymentStrategy(new PayPalPayment());
                case "2" -> context.setPaymentStrategy(new BankTransferPayment());
                case "3" -> context.setPaymentStrategy(new CreditCardPayment());
                default -> {
                    log.warn("Opción inválida");
                    return;
                }
            }

            final int maxAttempts = 3;
            boolean isValid = false;

            for (int attempts = 1; attempts <= maxAttempts; attempts++) {
                log.info("Intento {}/{}", attempts, maxAttempts);
                context.collectPaymentDetails(reader);

                if (context.validatePaymentDetails()) {
                    isValid = true;
                    break;
                }
            }

            if (!isValid) {
                log.warn("Se consumieron todos los intentos. Inténtelo más tarde.");
                return;
            }

            log.info("Ingrese el monto a pagar:");
            int amount = Integer.parseInt(reader.readLine().trim());

            if (context.pay(amount)) {
                log.info("Pago realizado con éxito");
            } else {
                log.info("El pago no se pudo procesar");
            }
        }
    }
}
