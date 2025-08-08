package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.exception;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class SystemMaintenanceException extends BusinessValidationException {
    public SystemMaintenanceException(LocalTime startTime, LocalTime endTime) {
        super("Sistema en mantenimiento. No se procesan pedidos desde la %s hasta las %s".
                formatted(formatTime(startTime), formatTime(endTime)));
    }

    private static String formatTime(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm a"));
    }
}
