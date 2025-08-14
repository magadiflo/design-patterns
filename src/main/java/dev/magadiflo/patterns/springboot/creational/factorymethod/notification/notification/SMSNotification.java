package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class SMSNotification implements Notification {

    private final String apiKey = "twilio_api_key_abc123xyz";
    private final String provider = "Twilio";

    @Override
    public void send(String message, String recipient) {
        log.info("=== SMS Notification ===");
        log.info("Provider: {}", this.provider);
        log.info("API Key: {}", this.apiKey.substring(0, 8) + "...");
        log.info("To: {}", recipient);
        log.info("Message: {}", message);

        this.simulateSMSSending(message, recipient);
        log.info("SMS enviado exitosamente a {}", recipient);
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.SMS;
    }

    private void simulateSMSSending(String message, String recipient) {
        try {
            log.info("Simulando el envío SMS, [mensaje]: {}, [a]: {}", message, recipient);
            Thread.sleep(Duration.ofSeconds(1));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Envío de SMS interrumpido", e);
        }
    }
}
