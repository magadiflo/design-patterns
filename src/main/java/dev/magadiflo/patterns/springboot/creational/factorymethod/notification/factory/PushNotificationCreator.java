package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.factory;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.Notification;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.notification.PushNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service("pushNotificationCreator")
public class PushNotificationCreator extends NotificationCreator {

    private final PushNotification pushNotification;

    @Override
    public Notification createNotification() {
        return this.pushNotification;
    }
}
