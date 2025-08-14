package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class WhatsAppNotification implements Notification {

    private final String businessApiKey = "whatsapp_business_key_ghi789";
    private final String phoneNumberId = "1234567890";

    @Override
    public void send(String message, String recipient) {
        log.info("=== WhatsApp Notification ===");
        log.info("Business API KEY: {}", this.businessApiKey.substring(0, 12) + "...");
        log.info("Phone Number ID: {}", this.phoneNumberId);
        log.info("To: {}", recipient);
        log.info("Message: {}", message);

        this.simulateWhatsAppSending(message, recipient);
        log.info("Mensaje de WhatsApp enviado exitosamente a {}", recipient);
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.PUSH;
    }

    private void simulateWhatsAppSending(String message, String recipient) {
        try {
            log.info("Simulando envío de WhatsApp, [mensaje]: {}, [a]: {}", message, recipient);
            Thread.sleep(Duration.ofMillis(800));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("El envío de WhatsApp se interrumpió", e);
        }
    }
}
