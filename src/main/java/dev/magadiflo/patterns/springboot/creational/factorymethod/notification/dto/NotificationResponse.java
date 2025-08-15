package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;

import java.time.LocalDateTime;

public record NotificationResponse(boolean success,
                                   String message,
                                   NotificationChannel channel,
                                   String recipient,
                                   LocalDateTime timestamp) {

    public static NotificationResponse success(String message, NotificationChannel channel, String recipient) {
        return new NotificationResponse(true, message, channel, recipient, LocalDateTime.now());
    }

    public static NotificationResponse failure(String message, NotificationChannel channel, String recipient) {
        return new NotificationResponse(false, message, channel, recipient, LocalDateTime.now());
    }
}
