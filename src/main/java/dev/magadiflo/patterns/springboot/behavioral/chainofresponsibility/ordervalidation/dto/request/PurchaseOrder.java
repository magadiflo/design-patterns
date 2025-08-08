package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record PurchaseOrder(@NotNull
                            @Positive
                            Long customerId,

                            @NotEmpty
                            @Valid
                            List<OrderItem> items,

                            @NotNull
                            @Valid
                            ShippingAddress shippingAddress) {
}
