package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.Notification;
import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.model.SMSNotification;

public class SMSNotificationCreator extends NotificationCreator {
    @Override
    public Notification createNotification() {
        return new SMSNotification("twilio_api_key_abc123xyz", "Twilio");
    }
}
