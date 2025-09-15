package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.lazy.threadsafe;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheManager {

    private static volatile CacheManager instance;

    private CacheManager() {
        log.info("Instancia creada");
    }

    public static CacheManager getInstance() {
        if (instance == null) { // check 1
            synchronized (CacheManager.class) {
                if (instance == null) { // check 2
                    instance = new CacheManager();
                }
            }
        }
        return instance;
    }
}
