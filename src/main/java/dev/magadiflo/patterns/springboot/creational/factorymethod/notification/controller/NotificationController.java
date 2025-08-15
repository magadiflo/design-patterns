package dev.magadiflo.patterns.springboot.creational.factorymethod.notification.controller;

import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto.NotificationRequest;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.dto.NotificationResponse;
import dev.magadiflo.patterns.springboot.creational.factorymethod.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationResponse> sendNotification(@Valid @RequestBody NotificationRequest request) {
        log.info("Se recibió una solicitud para enviar {} notificación a {}", request.channel(), request.recipient());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.notificationService.sendNotification(request));
    }

}
