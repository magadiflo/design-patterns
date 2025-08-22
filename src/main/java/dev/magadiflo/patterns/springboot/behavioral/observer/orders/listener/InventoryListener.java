package dev.magadiflo.patterns.springboot.behavioral.observer.orders.listener;

import dev.magadiflo.patterns.springboot.behavioral.observer.orders.event.OrderStatusChangedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryListener {

    @EventListener
    @Async("ordersExecutor")
    public void onOrderStatusChanged(OrderStatusChangedEvent event) {
        switch (event.status()) {
            case CONFIRMED -> log.info("Inventario: Reduciendo stock para {}", event.orderId());
            case CANCELLED -> log.info("Inventario: Restaurando stock para {}", event.orderId());
            default -> { /* SHIPPED no cambia stock en este ejemplo */ }
        }
    }
}
