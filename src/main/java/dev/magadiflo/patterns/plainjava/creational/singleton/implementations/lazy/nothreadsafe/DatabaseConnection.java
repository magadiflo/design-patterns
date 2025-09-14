package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.lazy.nothreadsafe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DatabaseConnection {
    private static DatabaseConnection instance; //lazy initialization

    private DatabaseConnection() {
        log.info("Instancia creada");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
