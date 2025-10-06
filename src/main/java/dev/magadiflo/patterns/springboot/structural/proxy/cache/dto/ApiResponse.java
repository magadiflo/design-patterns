package dev.magadiflo.patterns.springboot.structural.proxy.cache.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(boolean success,
                             String message,
                             T data,
                             boolean cacheHit,
                             long executionTimeMs,
                             LocalDateTime timestamp) {

    public static <T> ApiResponse<T> create(boolean success, String message, T data, boolean cacheHit, long executionTimeMs) {
        return new ApiResponse<>(success, message, data, cacheHit, executionTimeMs, LocalDateTime.now());
    }
}
