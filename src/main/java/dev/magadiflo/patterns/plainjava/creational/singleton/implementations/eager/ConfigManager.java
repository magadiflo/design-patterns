package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.eager;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();

    private ConfigManager() {
    }

    public static ConfigManager getInstance() {
        return INSTANCE;
    }
}
