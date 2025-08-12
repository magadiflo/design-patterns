package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class EmailNotification implements Notification {

    private final String smtpServer;
    private final int port;
    private final String username;

    @Override
    public void send(String message, String recipient) {
        log.info("=== Email Notification ===");
        log.info("SMTP Server: {}:{}", this.smtpServer, this.port);
        log.info("From: {}", this.username);
        log.info("To: {}", recipient);
        log.info("Message: {}", message);
        log.info("Email sent successfully!");
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }
}
