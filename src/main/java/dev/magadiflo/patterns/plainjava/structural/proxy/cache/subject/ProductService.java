package dev.magadiflo.patterns.plainjava.structural.proxy.cache.subject;

import dev.magadiflo.patterns.plainjava.structural.proxy.cache.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> findByCategory(String category, int page);

    List<Product> getRelatedProducts(Long productId);

    List<Product> searchWithFilters(String category, BigDecimal minPrice, BigDecimal maxPrice);
}
