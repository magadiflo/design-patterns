package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.converter;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.BankTransferPaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.CreditCardPaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PayPalPaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.RawPaymentRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class PaymentRequestConverter {

    public PaymentRequest createPaymentRequest(RawPaymentRequest rawRequest) {
        log.info("Convirtiendo RawPaymentRequest a {}", rawRequest.paymentType());
        return switch (rawRequest.paymentType()) {
            case "bankTransfer" -> this.createBankTransferRequest(rawRequest);
            case "creditCard" -> this.createCreditCardRequest(rawRequest);
            case "payPal" -> this.createPayPalRequest(rawRequest);
            default -> throw new IllegalArgumentException("Tipo de pago no soportado: " + rawRequest.paymentType());
        };
    }

    private CreditCardPaymentRequest createCreditCardRequest(RawPaymentRequest rawRequest) {
        return new CreditCardPaymentRequest(
                rawRequest.orderId(),
                rawRequest.amount(),
                rawRequest.currency(),
                this.getTypedValue(rawRequest.paymentData(), "cardNumber"),
                this.getTypedValue(rawRequest.paymentData(), "expiryDate"),
                this.getTypedValue(rawRequest.paymentData(), "cvv"));
    }

    private PayPalPaymentRequest createPayPalRequest(RawPaymentRequest rawRequest) {
        return new PayPalPaymentRequest(
                rawRequest.orderId(),
                rawRequest.amount(),
                rawRequest.currency(),
                this.getTypedValue(rawRequest.paymentData(), "paypalEmail"),
                this.getTypedValue(rawRequest.paymentData(), "password"));
    }

    private BankTransferPaymentRequest createBankTransferRequest(RawPaymentRequest rawRequest) {
        return new BankTransferPaymentRequest(
                rawRequest.orderId(),
                rawRequest.amount(),
                rawRequest.currency(),
                this.getTypedValue(rawRequest.paymentData(), "accountHolder"),
                this.getTypedValue(rawRequest.paymentData(), "bankName"),
                this.getTypedValue(rawRequest.paymentData(), "accountNumber"));
    }

    private String getTypedValue(Map<String, Object> data, String key) {
        return data != null ? (String) data.get(key) : null;
    }
}
