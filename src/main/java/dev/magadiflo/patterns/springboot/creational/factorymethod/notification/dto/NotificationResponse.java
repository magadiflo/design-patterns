package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;

import java.time.LocalDateTime;

public record NotificationResponse(String message,
                                   NotificationChannel channel,
                                   String recipient,
                                   LocalDateTime timestamp) {
}
