package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.service;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto.NotificationRequest;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto.NotificationResponse;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.factory.NotificationFactory;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.Notification;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationFactory notificationFactory;

    @Override
    public NotificationResponse sendNotification(NotificationRequest request) {
        try {
            log.info("Procesando {} notificación para el destinatario {}", request.channel(), request.recipient());

            // Validación de lógica de negocio
            this.validateInput(request.message(), request.recipient());

            // Obtenemos el notificador concreto y enviamos notificación
            Notification notification = this.notificationFactory.getNotification(request.channel());
            notification.send(request.message(), request.recipient());

            // Pos-procesamiento
            log.info("Logged: {} notificación enviado a {}", request.channel(), request.recipient());

            return new NotificationResponse("Notificación enviada exitosamente", request.channel(), request.recipient(), LocalDateTime.now());
        } catch (Exception e) {
            final String message = "No se pudo enviar la notificación";
            log.error(message.concat(" {} a {}: {}"), request.channel(), request.recipient(), e.getMessage());
            throw new IllegalArgumentException(message.concat(" %s a %s: %s").formatted(request.channel(), request.recipient(), e.getMessage()));
        }
    }

    private void validateInput(@NotBlank String message, @NotBlank String recipient) {
        if (Objects.isNull(message) || message.isBlank()) {
            log.error("El mensaje no puede estar vacío");
            throw new IllegalArgumentException("El mensaje no puede ser vacío.");
        }

        if (Objects.isNull(recipient) || recipient.isBlank()) {
            log.error("El destinatario no puede estar vacío");
            throw new IllegalArgumentException("El destinatario no puede estar vacío.");
        }
    }

}
