package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.reflection.problem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventRegistry {

    private static final EventRegistry instance = new EventRegistry();

    private EventRegistry() {
        log.info("Instancia creada");
    }

    public static EventRegistry getInstance() {
        return instance;
    }

}
