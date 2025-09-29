package dev.magadiflo.patterns.plainjava.structural.proxy.cache.model;

import java.math.BigDecimal;

public record Product(Long id,
                      String name,
                      String category,
                      BigDecimal price,
                      Double rating,
                      String brand,
                      boolean available) {
}
