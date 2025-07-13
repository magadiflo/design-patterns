package dev.magadiflo.patterns.plainjava.behavioral.strategy.paymentmethod.strategy;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
public class BankTransferPayment implements PaymentStrategy {

    private String accountHolder;
    private String bankName;
    private String accountNumber;
    private boolean detailsProvided;

    @Override
    public void collectPaymentDetails(BufferedReader reader) {
        log.info("=== Pago por Transferencia Bancaria ===");
        try {
            this.accountHolder = this.prompt(reader, "Ingrese nombre del titular de la cuenta:");
            this.bankName = this.prompt(reader, "Ingrese nombre del banco:");
            this.accountNumber = this.prompt(reader, "Ingrese número de cuenta:");
        } catch (IOException exception) {
            log.error("Error al leer datos bancarios: {}", exception.getMessage());
        }
    }

    @Override
    public boolean validatePaymentDetails() {
        this.detailsProvided = this.isValid();
        if (this.detailsProvided) {
            log.info("Detalles bancarios registrados correctamente");
        } else {
            log.error("Faltan datos para procesar la transferencia");
        }
        return this.detailsProvided;
    }

    @Override
    public boolean pay(int paymentAmount) {
        if (this.detailsProvided) {
            log.info("Pagando S/ {} por transferencia bancaria a nombre de {} en {}.", paymentAmount, this.accountHolder, this.bankName);
            return true;
        }
        log.warn("No se proporcionaron los datos necesarios para realizar la transferencia");
        return false;
    }

    private boolean isValid() {
        return !accountHolder.isBlank() &&
               !bankName.isBlank() &&
               !accountNumber.isBlank();
    }

    private String prompt(BufferedReader reader, String message) throws IOException {
        log.info(message);
        return reader.readLine().trim();
    }
}
