package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.handler;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.SupportRequest;

public abstract class TerminalSupportHandler extends SupportHandler {

    @Override
    public void handle(SupportRequest request) {
        // Nunca delega, solo ejecuta lógica definida por la subclase
        this.handleUnsupported(request);
    }

    @Override
    protected final boolean canProcess(SupportRequest request) {
        // Nunca se llama. Lo hacemos final para bloquear la sobre-escritura
        return false;
    }

    protected abstract void handleUnsupported(SupportRequest request);
}
