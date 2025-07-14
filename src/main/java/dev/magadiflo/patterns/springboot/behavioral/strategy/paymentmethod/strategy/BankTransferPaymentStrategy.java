package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.strategy;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.BankTransferPaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Component("bankTransfer")
public class BankTransferPaymentStrategy implements PaymentStrategy<BankTransferPaymentRequest> {

    @Override
    public boolean validatePayment(BankTransferPaymentRequest request) {
        if (Objects.isNull(request.accountHolder()) || request.accountHolder().isBlank()) {
            log.error("Titular de la cuenta es requerido");
            return false;
        }

        if (Objects.isNull(request.bankName()) || request.bankName().isBlank()) {
            log.error("El nombre del banco es requerido");
            return false;
        }

        if (Objects.isNull(request.accountNumber()) || request.accountNumber().isBlank()) {
            log.error("Número de cuenta es requerido");
            return false;
        }

        log.info("Datos para la transferencia bancaria validados correctamente");
        return true;
    }

    @Override
    public PaymentResponse pay(BankTransferPaymentRequest request) {
        log.info("Pagando S/{} mediante transferencia bancaria al banco {}", request.amount(), request.bankName());
        try {
            Thread.sleep(Duration.ofMillis(800));
            String transactionId = "BT-" + System.currentTimeMillis();
            return new PaymentResponse(true, transactionId, "Pago procesado exitosamente con Transferencia Bancaria");
        } catch (InterruptedException e) {
            log.error("Error en el procesamiento de pago con Transferencia Bancaria");
            throw new RuntimeException(e);
        }
    }
}
