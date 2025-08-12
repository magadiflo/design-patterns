package dev.magadiflo.patterns.plainjava.creational.factorymethod.notification;

import dev.magadiflo.patterns.plainjava.creational.factorymethod.notification.factory.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    private static final String SEPARATOR = "================================================================";

    public static void main(String[] args) {
        log.info("FACTORY METHOD PATTERN - Sistema de Notificaciones Multi-canal");
        log.info(SEPARATOR);

        // Escenario 01: Confirmación de Pedido (Email)
        log.info("Escenario 01: Confirmación de Pedido");
        NotificationCreator emailCreator = new EmailNotificationCreator();
        emailCreator.processNotification(
                "Su pedido #415 ha sido confirmado. Total: S/ 1500.00",
                "cliente@example.com");


        // Escenario 02: Alerta urgente (SMS)
        log.info(SEPARATOR);
        log.info("Escenario 02: Alerta de seguridad");
        NotificationCreator smsCreator = new SMSNotificationCreator();
        smsCreator.processNotification(
                "Código de verificación: 123456. Válido por 5 minutos.",
                "+51946741485");


        // Escenario 03: Promoción (Push)
        log.info(SEPARATOR);
        log.info("Escenario 03: Notificación Push - Promoción");
        NotificationCreator pushCreator = new PushNotificationCreator();
        pushCreator.processNotification(
                "¡50% OFF en todos los productos! Aprovecha ahora.",
                "device_token_abc123xyz789");


        // Escenario 04: Soporte personalizado (WhatsApp)
        log.info(SEPARATOR);
        log.info("Escenario 04: Soporte WhatsApp");
        NotificationCreator whatsAppCreator = new WhatsAppNotificationCreator();
        whatsAppCreator.processNotification(
                "Hola! Su consulta ha sido recibida. Un agente le responderá pronto.",
                "+51987458778");
    }
}
