package dev.magadiflo.patterns.springboot.behavioral.observer.orders.listener;

import dev.magadiflo.patterns.springboot.behavioral.observer.orders.event.OrderStatusChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailListener {

    @EventListener
    @Async("ordersExecutor")
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        switch (event.status()) {
            case CONFIRMED -> log.info("Email: Pedido confirm. {} (corrId={})", event.orderId(), event.correlationId());
            case SHIPPED -> log.info("Email: Pedido {} enviado (corrId={})", event.orderId(), event.correlationId());
            case CANCELLED -> log.info("Email: Pedido cancel. {} (corrId={})", event.orderId(), event.correlationId());
        }
    }
}
