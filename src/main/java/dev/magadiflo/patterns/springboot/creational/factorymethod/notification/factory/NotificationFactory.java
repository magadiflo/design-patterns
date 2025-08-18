package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.model.NotificationChannel;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NotificationFactory {

    private final Map<NotificationChannel, Notification> notificationMap;

    public NotificationFactory(List<Notification> notifications) {
        this.notificationMap = notifications.stream()
                .collect(Collectors.toMap(
                        Notification::getChannel,                         // La clave del mapa será el canal
                        Function.identity(),                                        // El valor del mapa será la notificación concreta
                        (existing, replacement) -> existing,   // Estrategia de fusión en caso de duplicados, se mantiene el existente
                        () -> new EnumMap<>(NotificationChannel.class)      // Proporciona el tipo de mapa a usar
                ));
        log.info("NotificationFactory inicializado con canales soportados: {}", this.notificationMap.keySet());
    }

    public Notification getNotification(NotificationChannel channel) {
        Notification notification = this.notificationMap.get(channel);
        if (Objects.isNull(notification)) {
            throw new IllegalArgumentException("Canal de notificación no soportado: " + channel);
        }
        return notification;
    }
}
