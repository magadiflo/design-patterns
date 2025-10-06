package dev.magadiflo.patterns.springboot.structural.proxy.cache.cache;

public record CacheResult<T>(T data,
                             boolean cacheHit,
                             long executionTimeMs) {
}
