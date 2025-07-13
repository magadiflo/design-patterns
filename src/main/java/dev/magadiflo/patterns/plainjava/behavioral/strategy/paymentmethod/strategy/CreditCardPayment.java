package dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy;

import dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy.model.CreditCard;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

@Slf4j
public class CreditCardPayment implements PaymentStrategy {

    private CreditCard creditCard;

    @Override
    public void collectPaymentDetails(BufferedReader reader) {
        log.info("=== Ingreso de datos de tarjeta de crédito ===");
        try {
            String number = this.prompt(reader, "Ingrese número de tarjeta:");
            String expiry = this.prompt(reader, "Ingrese fecha de expiración (mm/yy):");
            String cvv = this.prompt(reader, "Ingrese código cvv:");
            this.creditCard = new CreditCard(number, expiry, cvv);
        } catch (IOException exception) {
            log.error("Error al leer datos de la tarjeta: {}", exception.getMessage());
        }
    }

    @Override
    public boolean validatePaymentDetails() {
        log.info("Validando tarjeta...");
        if (this.validateCard(this.creditCard)) {
            log.info("Tarjeta válida: {}", creditCard);
            return true;
        }

        log.error("Datos de tarjeta inválidos");
        this.creditCard = null;
        return false;
    }

    @Override
    public boolean pay(int paymentAmount) {
        if (!this.cardIsPresent()) {
            log.warn("No hay tarjeta registrada. No se puede procesar el pago");
            return false;
        }

        if (this.creditCard.getAmount() < paymentAmount) {
            log.warn("Fondos insuficientes en la tarjeta. Monto disponible: S/ {}", this.creditCard.getAmount());
            return false;
        }

        this.creditCard.setAmount(this.creditCard.getAmount() - paymentAmount);
        log.info("Pagando S/ {} con tarjeta de crédito (Saldo restante: S/ {})", paymentAmount, this.creditCard.getAmount());
        return true;

    }

    private boolean validateCard(CreditCard card) {
        return !card.getNumber().isBlank() &&
               !card.getExpiry().isBlank() &&
               !card.getCvv().isBlank();
    }

    private boolean cardIsPresent() {
        return Objects.nonNull(this.creditCard);
    }

    private String prompt(BufferedReader reader, String message) throws IOException {
        log.info(message);
        return reader.readLine().trim();
    }
}
