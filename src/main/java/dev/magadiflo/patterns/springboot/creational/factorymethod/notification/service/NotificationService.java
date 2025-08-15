package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.service;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto.NotificationRequest;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto.NotificationResponse;

public interface NotificationService {
    NotificationResponse sendNotification(NotificationRequest request);
}
