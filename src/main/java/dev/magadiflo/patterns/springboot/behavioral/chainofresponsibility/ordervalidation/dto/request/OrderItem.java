package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record OrderItem(@NotNull
                        @Positive
                        Long productId,

                        @NotNull
                        @Positive
                        Integer quantity,

                        @NotNull
                        @DecimalMin(value = "0.01")
                        BigDecimal unitPrice) {

    public BigDecimal getSubTotal() {
        return this.unitPrice
                .multiply(BigDecimal.valueOf(this.quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
