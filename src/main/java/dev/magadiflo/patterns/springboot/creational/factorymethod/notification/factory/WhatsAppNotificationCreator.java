package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.Notification;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.WhatsAppNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("whatsAppNotificationCreator")
public class WhatsAppNotificationCreator extends NotificationCreator {

    private final WhatsAppNotification whatsAppNotification;

    @Override
    public Notification createNotification() {
        return this.whatsAppNotification;
    }
}
