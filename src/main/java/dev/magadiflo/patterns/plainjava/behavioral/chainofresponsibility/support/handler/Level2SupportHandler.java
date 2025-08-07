package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.handler;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.IssueType;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.SupportRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Level2SupportHandler extends SupportHandler {
    @Override
    protected boolean canProcess(SupportRequest request) {
        if (IssueType.NETWORK.equals(request.issueType())) {
            log.info("Nivel 2 resolvió el problema de red para: {}", request.employeeName());
            return true;
        }
        return false;
    }
}
