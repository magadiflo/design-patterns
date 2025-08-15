package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.service;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto.NotificationRequest;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto.NotificationResponse;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.factory.NotificationCreator;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationCreator emailCreator;
    private final NotificationCreator smsCreator;
    private final NotificationCreator pushCreator;
    private final NotificationCreator whatsAppCreator;

    public NotificationServiceImpl(@Qualifier("emailNotificationCreator") NotificationCreator emailCreator,
                                   @Qualifier("sMSNotificationCreator") NotificationCreator smsCreator,
                                   @Qualifier("pushNotificationCreator") NotificationCreator pushCreator,
                                   @Qualifier("whatsAppNotificationCreator") NotificationCreator whatsAppCreator) {
        this.emailCreator = emailCreator;
        this.smsCreator = smsCreator;
        this.pushCreator = pushCreator;
        this.whatsAppCreator = whatsAppCreator;
    }

    @Override
    public NotificationResponse sendNotification(NotificationRequest request) {
        try {
            log.info("Procesando {} notificación para el destinatario {}", request.channel(), request.recipient());

            NotificationCreator creator = this.getNotificationCreator(request.channel());
            creator.processNotification(request.message(), request.recipient());

            log.info("Notificación enviada exitosamente vía {} a {}", request.channel(), request.recipient());
            return NotificationResponse.success("Notificación enviada exitosamente", request.channel(), request.recipient());
        } catch (Exception e) {

            log.error("No se pudo enviar la notificación {} a {}: {}",
                    request.channel(), request.recipient(), e.getMessage());
            return NotificationResponse.failure("No se pudo enviar la notificación: " + e.getMessage(), request.channel(), request.recipient());
        }
    }

    private NotificationCreator getNotificationCreator(NotificationChannel channel) {
        return switch (channel) {
            case NotificationChannel.EMAIL -> this.emailCreator;
            case NotificationChannel.SMS -> this.smsCreator;
            case NotificationChannel.PUSH -> this.pushCreator;
            case NotificationChannel.WHATSAPP -> this.whatsAppCreator;
        };
    }
}
