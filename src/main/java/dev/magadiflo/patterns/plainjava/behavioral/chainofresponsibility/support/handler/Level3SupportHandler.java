package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.handler;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.IssueType;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.SupportRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Level3SupportHandler extends SupportHandler {
    @Override
    protected boolean canProcess(SupportRequest request) {
        if (IssueType.HARDWARE.equals(request.issueType())) {
            log.info("Nivel 3 resolvió el problema de hardware para: {}", request.employeeName());
            return true;
        }
        return false;
    }
}
