package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.client;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.handler.*;
import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.support.model.SupportRequest;

public class SupportClient {

    private final SupportHandler firstHandler;

    public SupportClient() {
        SupportHandler level1 = new Level1SupportHandler();
        SupportHandler level2 = new Level2SupportHandler();
        SupportHandler level3 = new Level3SupportHandler();
        SupportHandler byDefault = new DefaultSupportHandler();

        level1.setNextHandler(level2)
                .setNextHandler(level3)
                .setNextHandler(byDefault);

        this.firstHandler = level1;
    }

    public void processRequest(SupportRequest request) {
        this.firstHandler.handle(request);
    }
}

