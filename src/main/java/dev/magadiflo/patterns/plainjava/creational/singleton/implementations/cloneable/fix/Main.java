package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.cloneable.fix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        UserPreferences userPreferences1 = UserPreferences.getInstance();
        UserPreferences userPreferences2 = UserPreferences.getInstance();
        log.info("{}", System.identityHashCode(userPreferences1));
        log.info("{}", System.identityHashCode(userPreferences2));

        // Código generado de la copia clonada no coincide
        UserPreferences userPreference3 = (UserPreferences) userPreferences2.clone();
        log.info("{}", System.identityHashCode(userPreference3));
    }
}
