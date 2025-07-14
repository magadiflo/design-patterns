package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.strategy;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.CreditCardPaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Component("creditCard")
public class CreditCardPaymentStrategy implements PaymentStrategy<CreditCardPaymentRequest> {

    @Override
    public boolean validatePayment(CreditCardPaymentRequest request) {
        if (Objects.isNull(request.cardNumber()) || request.cardNumber().trim().length() < 16) {
            log.error("Número de tarjeta inválido");
            return false;
        }

        if (Objects.isNull(request.expiryDate()) || request.expiryDate().isBlank()) {
            log.error("Fecha de expiración requerida");
            return false;
        }

        if (Objects.isNull(request.cvv()) || request.cvv().trim().length() != 3) {
            log.error("Cvv inválido");
            return false;
        }

        log.info("Datos de la tarjeta crédito validados correctamente");
        return true;
    }

    @Override
    public PaymentResponse pay(CreditCardPaymentRequest request) {
        log.info("Procesando pago S/{} con tarjeta de crédito {}", request.amount(), request.cardNumber());
        try {
            Thread.sleep(Duration.ofSeconds(1));
            String transactionId = "CC-" + System.currentTimeMillis();
            return new PaymentResponse(true, transactionId, "Pago procesado exitosamente con tarjeta");
        } catch (InterruptedException e) {
            log.error("Error en el procesamiento de pago con tarjeta");
            throw new RuntimeException(e);
        }
    }
}
