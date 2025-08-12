package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.Notification;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public abstract class NotificationCreator {

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
        log.info("Logged: {} notificación enviado a {}", notification.getChannel(), recipient);
    }

    // Factory Method - debe ser implementado por las subclases
    public abstract Notification createNotification();
}
