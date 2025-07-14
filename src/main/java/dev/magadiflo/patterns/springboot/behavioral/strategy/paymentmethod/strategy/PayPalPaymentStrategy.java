package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.strategy;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PayPalPaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Component("payPal")
public class PayPalPaymentStrategy implements PaymentStrategy<PayPalPaymentRequest> {
    @Override
    public boolean validatePayment(PayPalPaymentRequest request) {
        if (Objects.isNull(request.paypalEmail()) || !request.paypalEmail().contains("@")) {
            log.error("Email de PayPal inválido");
            return false;
        }

        if (Objects.isNull(request.password()) || request.password().length() < 6) {
            log.error("Contraseña debe tener al menos 6 caracteres");
            return false;
        }

        log.info("Datos de la cuenta de PayPal validados correctamente");
        return true;
    }

    @Override
    public PaymentResponse pay(PayPalPaymentRequest request) {
        log.info("Procesando pago S/{} con PayPal para el usuario {}", request.amount(), request.paypalEmail());
        try {
            Thread.sleep(Duration.ofMillis(800));
            String transactionId = "PP-" + System.currentTimeMillis();
            return new PaymentResponse(true, transactionId, "Pago procesado exitosamente con PayPal");
        } catch (InterruptedException e) {
            log.error("Error en el procesamiento de pago con PayPal");
            throw new RuntimeException(e);
        }
    }
}
