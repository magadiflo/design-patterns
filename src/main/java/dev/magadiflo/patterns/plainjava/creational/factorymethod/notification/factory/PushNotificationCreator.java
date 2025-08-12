package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.Notification;
import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.PushNotification;

public class PushNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new PushNotification("firebase_server_key_def456", "com.company.app");
    }
}
