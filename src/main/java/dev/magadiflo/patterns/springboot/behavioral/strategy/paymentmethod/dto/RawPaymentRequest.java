package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.Map;

public record RawPaymentRequest(@NotBlank(message = "El Id del pedido es obligatorio")
                                String orderId,

                                @NotNull(message = "El monto no puede ser nulo")
                                @Positive(message = "El monto debe ser mayor que cero")
                                Double amount,

                                @NotBlank(message = "La moneda es obligatoria")
                                String currency,

                                @NotBlank(message = "El tipo de pago es obligatorio")
                                String paymentType,

                                @NotNull(message = "Los datos de pago son obligatorios")
                                Map<String, Object> paymentData) {
}
