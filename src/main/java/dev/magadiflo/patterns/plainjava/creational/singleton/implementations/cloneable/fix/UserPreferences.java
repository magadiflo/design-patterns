package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.cloneable.fix;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserPreferences implements Cloneable {
    private static final UserPreferences instance = new UserPreferences();

    private UserPreferences() {
        log.info("Instancia creada");
    }

    public static UserPreferences getInstance() {
        return instance;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instance != null) {
            throw new CloneNotSupportedException("No se puede crear la instancia. Utilice getInstance() para crearla.");
        }
        return super.clone();
    }
}
