package dev.magadiflo.patterns.plainjava.creational.singleton.implementations.lazy.nothreadsafe;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class Main {
    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            IntStream.range(0, 10).forEach(value -> {
                Runnable task = () -> {
                    DatabaseConnection connection = DatabaseConnection.getInstance();
                    log.info("Thread-{} -> {}", value, System.identityHashCode(connection));
                };
                executorService.submit(task);
            });
            executorService.shutdown();
        }
    }
}
