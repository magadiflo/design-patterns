package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.eager;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j
public class Main {
    public static void main(String[] args) {
        IntStream.range(0, 10).forEach(value -> {
            Runnable task = () -> {
                ConfigManager configManager = ConfigManager.getInstance();
                log.info("Thread-{} -> {}", value, System.identityHashCode(configManager));
            };
            Thread.ofPlatform().start(task);
        });
    }
}
