package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.lazy.threadsafe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Logger {

    private static Logger instance;

    private Logger() {
        log.info("Instancia creada");
    }

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
}
