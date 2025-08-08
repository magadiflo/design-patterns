package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.response;

import java.math.BigDecimal;

public record OrderResponse(String orderId,
                            String status,
                            String message,
                            BigDecimal totalAmount) {
}
