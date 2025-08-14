package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class PushNotification implements Notification {

    private final String firebaseKey = "firebase_server_key_def456";
    private final String appId = "com.company.app";

    @Override
    public void send(String message, String recipient) {
        log.info("=== Push Notification ===");
        log.info("Firebase Key: {}", this.firebaseKey.substring(0, 10) + "...");
        log.info("APP ID: {}", this.appId);
        log.info("Device Token: {}", recipient);
        log.info("Message: {}", message);

        this.simulatePushSending(message, recipient);
        log.info("Notificación push enviada exitosamente a {}", recipient);
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.PUSH;
    }

    private void simulatePushSending(String message, String recipient) {
        try {
            log.info("Simulando envío de notificación push, [mensaje]: {}, [a]: {}", message, recipient);
            Thread.sleep(Duration.ofMillis(1500));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Envío de notificaciones push interrumpido", e);
        }
    }
}
