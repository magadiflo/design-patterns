package dev.magadiflo.patterns.springboot.structural.proxy.cache.cache;

import lombok.Getter;

import java.time.LocalDateTime;

public class CacheEntry<T> {
    @Getter
    private final T data;
    @Getter
    private final LocalDateTime timestamp;
    private final int ttlMinutes;

    public CacheEntry(T data, int ttlMinutes) {
        this.timestamp = LocalDateTime.now();
        this.data = data;
        this.ttlMinutes = ttlMinutes;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.timestamp.plusMinutes(this.ttlMinutes));
    }

}
