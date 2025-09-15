package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.cloneable.problem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MailSender implements Cloneable {
    private static final MailSender instance = new MailSender();

    private MailSender() {
        log.info("Instancia creada");
    }

    public static MailSender getInstance() {
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
