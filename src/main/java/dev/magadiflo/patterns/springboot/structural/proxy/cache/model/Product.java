package dev.magadiflo.patterns.springboot.structural.proxy.cache.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {
    private Long id;
    private String name;
    private String category;
    private BigDecimal price;
    private Double rating;
    private String brand;
    private boolean available;
}
