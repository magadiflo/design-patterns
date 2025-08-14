package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class EmailNotification implements Notification {

    private final String smtpServer = "smtp.company.com";
    private final int port = 587;
    private final String username = "notifications@company.com";

    @Override
    public void send(String message, String recipient) {
        log.info("=== Email Notification ===");
        log.info("SMTP Server: {}:{}", this.smtpServer, this.port);
        log.info("From: {}", this.username);
        log.info("To: {}", recipient);
        log.info("Message: {}", message);

        this.simulateEmailSending(message, recipient);
        log.info("Correo electrónico enviado exitosamente a {}", recipient);
    }

    @Override
    public NotificationChannel getChannel() {
        return NotificationChannel.EMAIL;
    }

    private void simulateEmailSending(String message, String recipient) {
        try {
            log.info("Simulando el envío de correo, [mensaje]: {}, [a]: {}", message, recipient);
            Thread.sleep(Duration.ofSeconds(2));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Envío de correo electrónico interrumpido", e);
        }
    }
}
