package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.handler;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.dto.request.PurchaseOrder;
import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception.SystemMaintenanceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Slf4j
@RequiredArgsConstructor
@Component
public class BusinessHoursValidationHandler extends BaseOrderValidationHandler {
    @Override
    protected void doHandle(PurchaseOrder order) {
        log.info("[BusinessHoursValidation] Verificando horario de procesamiento...");
        int hour = LocalTime.now().getHour();
        if (hour < 7 && hour >= 1) {
            LocalTime startTime = LocalTime.of(1, 0);
            LocalTime endTime = LocalTime.of(6, 59);
            throw new SystemMaintenanceException(startTime, endTime);
        }
        log.info("[BusinessHoursValidation] Horario válido para el procesamiento");
    }
}
