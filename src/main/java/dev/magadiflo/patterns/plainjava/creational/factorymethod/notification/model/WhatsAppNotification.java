package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class WhatsAppNotification implements Notification {

    private final String businessApiKey;
    private final String phoneNumberId;

    @Override
    public void send(String message, String recipient) {
        log.info("=== WhatsApp Notification ===");
        log.info("Business API KEY: {}", this.businessApiKey.substring(0, 12) + "...");
        log.info("Phone Number ID: {}", this.phoneNumberId);
        log.info("To: {}", recipient);
        log.info("Message: {}", message);
        log.info("WhatsApp message sent successfully!");
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.WHATSAPP;
    }
}
