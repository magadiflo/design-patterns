package dev.magadiflo.patterns.springboot.behavioral.observer.orders.controller;

import dev.magadiflo.patterns.springboot.behavioral.observer.orders.model.OrderStatus;
import dev.magadiflo.patterns.springboot.behavioral.observer.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v2/orders")
public class OrderRestController {

    private final OrderService orderService;

    @PostMapping(path = "/{orderId}/status")
    public void updateStatus(@PathVariable String orderId, @RequestParam OrderStatus status) {
        this.orderService.changeOrderStatus(orderId, status, UUID.randomUUID().toString().replace("-", ""));
    }
}
