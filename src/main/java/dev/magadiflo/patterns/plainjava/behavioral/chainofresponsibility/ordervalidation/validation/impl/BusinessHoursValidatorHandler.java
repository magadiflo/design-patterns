package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.impl;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.dto.ValidationResult;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.PurchaseOrder;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.validation.handler.BaseOrderValidationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;

@Slf4j
@RequiredArgsConstructor
public class BusinessHoursValidatorHandler extends BaseOrderValidationHandler {
    @Override
    protected ValidationResult doHandle(PurchaseOrder order) {
        log.info("[Validador de horario comercial] Verificando horario de procesamiento...");
        int hour = LocalTime.now().getHour();

        // Bloqueamos pedidos entre la 1:00 AM (inclusive) y 7:00 AM (exclusive)
        if (hour < 7 && hour >= 1) {
            return ValidationResult.failure(
                    "Sistema en mantenimiento. No se procesan pedidos desde la 1:00 am hasta las 06:59 am",
                    "SYSTEM_MAINTENANCE");
        }

        log.info("Horario válido para el procesamiento");
        return ValidationResult.success();
    }
}
