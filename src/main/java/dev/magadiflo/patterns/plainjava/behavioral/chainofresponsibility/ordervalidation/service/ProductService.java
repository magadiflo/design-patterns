package dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.service;

import dev.magadiflo.patterns.plainjava.behavioral.chainofresponsibility.ordervalidation.model.Product;

import java.util.Map;

// Simula acceso a base de datos
public class ProductService {
    private static final Map<Long, Product> products = Map.of(
            101L, new Product(101L, "Dell Laptop", 1200.00, 5),
            102L, new Product(102L, "Wireless Mouse", 25.50, 0), // No hay stock (agotado)
            103L, new Product(103L, "Mechanical Keyboard", 89.99, 10)
    );

    public Product findById(Long id) {
        return products.get(id);
    }
}
