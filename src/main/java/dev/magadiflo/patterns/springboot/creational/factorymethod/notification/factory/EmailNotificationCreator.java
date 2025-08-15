package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.EmailNotification;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("emailNotificationCreator")
public class EmailNotificationCreator extends NotificationCreator {

    private final EmailNotification emailNotification;

    @Override
    public Notification createNotification() {
        return this.emailNotification;
    }
}
