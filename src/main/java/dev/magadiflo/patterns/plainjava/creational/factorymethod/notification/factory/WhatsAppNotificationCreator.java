package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.Notification;
import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.WhatsAppNotification;

public class WhatsAppNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new WhatsAppNotification("whatsapp_business_key_ghi789", "943854789");
    }
}
