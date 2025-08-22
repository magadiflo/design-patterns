package dev.magadiflo.patterns.springboot.behavioral.observer.orders.listener;

import dev.magadiflo.patterns.springboot.behavioral.observer.orders.event.OrderStatusChangedEvent;
import dev.magadiflo.patterns.springboot.behavioral.observer.orders.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsListener {

    @EventListener
    @Async("ordersExecutor")
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        if (event.status() == OrderStatus.SHIPPED) {
            log.info("SMS: Pedido {} despachado - Track: TRK{}", event.orderId(), event.orderId());
        }
    }
}
