package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.reflection.problem;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
        EventRegistry registry1 = EventRegistry.getInstance();
        EventRegistry registry2 = EventRegistry.getInstance();
        log.info("{}", System.identityHashCode(registry1));
        log.info("{}", System.identityHashCode(registry2));

        // Usando reflexion
        Class<?> clazz = Class.forName("dev.magadiflo.patterns.plainjava.creational.singleton.implementations.reflection.problem.EventRegistry");
        Constructor<EventRegistry> constructor = (Constructor<EventRegistry>) clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        // En tiempo de ejecución creamos una nueva instancia de la clase EventRegistry
        EventRegistry registry3 = constructor.newInstance();
        log.info("{}", System.identityHashCode(registry3));
    }
}
