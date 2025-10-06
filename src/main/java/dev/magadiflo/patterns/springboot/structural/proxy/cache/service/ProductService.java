package dev.magadiflo.patterns.springboot.structural.proxy.cache.service;

import dev.magadiflo.patterns.springboot.structural.proxy.cache.cache.CacheResult;
import dev.magadiflo.patterns.springboot.structural.proxy.cache.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    CacheResult<List<Product>> findByCategory(String category, int page);

    CacheResult<List<Product>> getRelatedProducts(Long productId);

    CacheResult<List<Product>> searchWithFilters(String category, BigDecimal minPrice, BigDecimal maxPrice);
}
