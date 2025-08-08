package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.response;

import java.time.LocalDateTime;

public record ErrorResponse(String errorCode,
                            String message,
                            LocalDateTime timestamp) {
}
