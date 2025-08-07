package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.handler;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.IssueType;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.SupportRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Level1SupportHandler extends SupportHandler {
    @Override
    protected boolean canProcess(SupportRequest request) {
        if (IssueType.PASSWORD.equals(request.issueType())) {
            log.info("Nivel 1 resolvió el problema de contraseña para: {}", request.employeeName());
            return true;
        }
        return false;
    }
}
