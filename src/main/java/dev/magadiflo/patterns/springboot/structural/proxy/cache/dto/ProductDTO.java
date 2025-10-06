package dev.magadiflo.patterns.springboot.structural.proxy.cache.dto;

import java.math.BigDecimal;

public record ProductDTO(Long id,
                         String name,
                         String category,
                         BigDecimal price,
                         Double rating,
                         String brand) {
}
