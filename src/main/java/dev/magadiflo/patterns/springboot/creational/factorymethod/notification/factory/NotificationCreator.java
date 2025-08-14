package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.Notification;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class NotificationCreator {

    public void processNotification(String message, String recipient) {
        Notification notification = this.createNotification();

        // Validación de lógica de negocio
        this.validateInput(message, recipient);

        // Enviar notificación
        notification.send(message, recipient);

        // Pos-procesamiento
        log.info("Logged: {} notificación enviado a {}", notification.getChannel(), recipient);
    }

    private void validateInput(String message, String recipient) {
        if (Objects.isNull(message) || message.isBlank()) {
            log.error("El mensaje no puede estar vacío");
            throw new IllegalArgumentException("El mensaje no puede ser vacío.");
        }

        if (Objects.isNull(recipient) || recipient.isBlank()) {
            log.error("El destinatario no puede estar vacío");
            throw new IllegalArgumentException("El destinatario no puede estar vacío.");
        }
    }

    public abstract Notification createNotification();
}
