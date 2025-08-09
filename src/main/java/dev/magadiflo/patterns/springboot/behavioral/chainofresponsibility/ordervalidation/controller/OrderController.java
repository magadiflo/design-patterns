package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.controller;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.response.OrderResponse;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service.OrderProcessingService;
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
@RequestMapping(path = "/api/v1/orders")
public class OrderController {

    private final OrderProcessingService orderProcessingService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody PurchaseOrder order) {
        log.info("Nueva orden recibida para el cliente: {}", order.customerId());
        OrderResponse orderResponse = this.orderProcessingService.processOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

}
