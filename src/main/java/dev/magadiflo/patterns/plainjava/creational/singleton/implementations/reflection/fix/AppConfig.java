package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.reflection.fix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppConfig {
    private final static AppConfig instance = new AppConfig();

    private AppConfig() {
        if (instance != null) {
            throw new RuntimeException("No se puede crear la instancia. Utilice getInstance() para crearla.");
        }
        log.info("Instancia creada");
    }

    public static AppConfig getInstance() {
        return instance;
    }
}
