package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.reflection.fix;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
        AppConfig appConfig1 = AppConfig.getInstance();
        AppConfig appConfig2 = AppConfig.getInstance();
        log.info("{}", System.identityHashCode(appConfig1));
        log.info("{}", System.identityHashCode(appConfig2));

        // Usando reflexion
        Class<?> clazz = Class.forName("dev.magadiflo.patterns.plainjava.creational.singleton.implementations.reflection.fix.AppConfig");
        Constructor<AppConfig> constructor = (Constructor<AppConfig>) clazz.getDeclaredConstructor();
        constructor.setAccessible(true);

        // En tiempo de ejecución creamos una nueva instancia de la clase EventRegistry
        AppConfig appConfig3 = constructor.newInstance();
        log.info("{}", System.identityHashCode(appConfig3));
    }
}
