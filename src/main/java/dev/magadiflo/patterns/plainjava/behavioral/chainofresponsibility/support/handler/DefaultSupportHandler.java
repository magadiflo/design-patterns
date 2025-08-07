package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.handler;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.SupportRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultSupportHandler extends TerminalSupportHandler {
    @Override
    protected void handleUnsupported(SupportRequest request) {
        log.warn("No hay soporte para el tipo de problema [{}] de [{}]", request.issueType(), request.employeeName());
        throw new UnsupportedOperationException("Solicitud de %s con tipo de problema [%s] no puede ser procesada".formatted(request.employeeName(), request.issueType()));
    }
}
