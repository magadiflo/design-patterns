package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationRequest(@NotNull
                                  NotificationChannel channel,
                                  @NotBlank
                                  String message,
                                  @NotBlank
                                  String recipient) {
}
