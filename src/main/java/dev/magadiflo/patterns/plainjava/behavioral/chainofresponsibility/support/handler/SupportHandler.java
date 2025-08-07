package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.handler;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.SupportRequest;

import java.util.Objects;

public abstract class SupportHandler {

    protected SupportHandler nextHandler;

    public SupportHandler setNextHandler(SupportHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    public void handle(SupportRequest request) {
        if (!this.canProcess(request) && Objects.nonNull(this.nextHandler)) {
            this.nextHandler.handle(request);
        }
    }

    protected abstract boolean canProcess(SupportRequest request);
}
