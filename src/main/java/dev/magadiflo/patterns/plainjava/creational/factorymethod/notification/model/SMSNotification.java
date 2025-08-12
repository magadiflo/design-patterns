package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SMSNotification implements Notification {

    private final String apiKey;
    private final String provider;

    @Override
    public void send(String message, String recipient) {
        log.info("=== SMS Notification ===");
        log.info("Provider: {}", this.provider);
        log.info("API Key: {}", this.apiKey.substring(0, 8) + "...");
        log.info("To: {}", recipient);
        log.info("Message: {}", message);
        log.info("SMS sent successfully!");
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }
}
