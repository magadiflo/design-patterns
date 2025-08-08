package dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.service;

import dev.magadiflo.patterns.springboot.behavioral.chainofresponsibility.ordervalidation.entity.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
    private final Map<Long, Product> products = Map.of(
            101L, new Product(101L, "Dell Laptop", new BigDecimal("1200.00"), 5),
            102L, new Product(102L, "Wireless Mouse", new BigDecimal("25.50"), 0),
            103L, new Product(103L, "Mechanical Keyboard", new BigDecimal("89.99"), 10)
    );

    public Product findById(Long productId) {
        return this.products.get(productId);
    }
}
