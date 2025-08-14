package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;

public interface Notification {
    void send(String message, String recipient);

    NotificationChannel getChannel();
}
