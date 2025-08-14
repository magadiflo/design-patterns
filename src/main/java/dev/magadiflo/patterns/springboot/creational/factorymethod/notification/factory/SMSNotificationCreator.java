package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.Notification;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.SMSNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SMSNotificationCreator extends NotificationCreator {

    private final SMSNotification smsNotification;

    @Override
    public Notification createNotification() {
        return this.smsNotification;
    }
}
