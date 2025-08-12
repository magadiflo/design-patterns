package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PushNotification implements Notification {

    private final String firebaseKey;
    private final String appId;

    @Override
    public void send(String message, String recipient) {
        log.info("=== Push Notification ===");
        log.info("Firebase Key: {}", this.firebaseKey.substring(0, 10) + "...");
        log.info("APP ID: {}", this.appId);
        log.info("Device Token: {}", recipient);
        log.info("Message: {}", message);
        log.info("Push notification sent successfully!");
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.PUSH;
    }
}
