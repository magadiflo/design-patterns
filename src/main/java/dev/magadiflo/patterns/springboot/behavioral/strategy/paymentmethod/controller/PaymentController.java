package dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.controller;

import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.PaymentResponse;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.dto.RawPaymentRequest;
import dev.magadiflo.patterns.springboot.behavioral.strategy.paymentmethod.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping(path = "/supported-types")
    public ResponseEntity<List<String>> getSupportedPaymentTypes() {
        return ResponseEntity.ok(this.paymentService.getSupportedPaymentTypes());
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody RawPaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.paymentService.processPayment(request));
    }
}
