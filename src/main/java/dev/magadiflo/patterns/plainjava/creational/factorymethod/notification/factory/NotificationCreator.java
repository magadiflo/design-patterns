package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.Notification;
import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.NotificationChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class NotificationCreator {

    // Factory Method - debe ser implementado por las subclases
    public abstract Notification createNotification();

    public void processNotification(String message, String recipient) {
        // Usar el factory method para crear la notificación apropiada
        Notification notification = this.createNotification();

        // Lógica de negocio adicional antes del envío
        log.info("Procesando {} notification...", notification.getChannel());

        // Validaciones generales (lógica de negocio común)
        if (Objects.isNull(message) || message.isBlank()) {
            log.error("El mensaje no puede estar vacío");
            return;
        }

        if (Objects.isNull(recipient) || recipient.isBlank()) {
            log.error("El destinatario no puede estar vacío");
            return;
        }

        // Enviar la notificación usando polimorfismo
        notification.send(message, recipient);

        // Lógica de negocio después del envío (logging, métricas, etc)
        this.logNotification(notification.getChannel(), recipient);
    }

    private void logNotification(NotificationChannel channel, String recipient) {
        log.info("Logged: {} notificación enviado a {}", channel, recipient);
    }
}
