package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.response.OrderResponse;

public interface OrderProcessingService {
    OrderResponse processOrder(PurchaseOrder order);
}
