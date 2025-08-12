package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.EmailNotification;
import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.Notification;

public class EmailNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new EmailNotification("smtp.company.com", 587, "notifications@company.com");
    }
}
