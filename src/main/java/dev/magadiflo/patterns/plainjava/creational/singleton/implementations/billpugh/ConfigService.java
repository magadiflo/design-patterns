package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.billpugh;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigService {
    private ConfigService() {
        log.info("Instancia creada");
    }

    public static ConfigService getInstance() {
        return SingletonHelper.INSTANCE;
    }

    private static class SingletonHelper {
        private static final ConfigService INSTANCE = new ConfigService();
    }
}
