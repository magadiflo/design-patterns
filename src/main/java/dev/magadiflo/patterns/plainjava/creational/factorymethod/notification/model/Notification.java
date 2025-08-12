package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model;

public interface Notification {
    void send(String message, String recipient);

    NotificationChannel getChannel();
}
