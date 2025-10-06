package dev.magadiflo.patterns.springboot.structural.proxy.cache.service;

import dev.magadiflo.patterns.springboot.structural.proxy.cache.cache.CacheResult;
import dev.magadiflo.patterns.springboot.structural.proxy.cache.mockdata.MockProductDatabase;
import dev.magadiflo.patterns.springboot.structural.proxy.cache.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final List<Product> database = MockProductDatabase.initializeDatabase();

    @Override
    public CacheResult<List<Product>> findByCategory(String category, int page) {
        log.info("Ejecutando consulta cara por categoría...");
        long startTime = System.currentTimeMillis();

        this.simulateExpensiveQuery();

        int pageSize = 5;
        int sartIndex = (page - 1) * pageSize;

        List<Product> data = database.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category) && product.isAvailable())
                .skip(sartIndex)
                .limit(pageSize)
                .toList();

        long executionTime = System.currentTimeMillis() - startTime;
        return new CacheResult<>(data, false, executionTime);
    }

    @Override
    public CacheResult<List<Product>> getRelatedProducts(Long productId) {
        log.info("Ejecutando consulta cara para productos relacionados...");
        long startTime = System.currentTimeMillis();

        this.simulateExpensiveQuery();

        Optional<Product> optionalProduct = this.findProductById(productId);
        List<Product> data = optionalProduct
                .map(p -> this.database.stream()
                        .filter(product -> !product.getId().equals(productId))
                        .filter(product -> product.getCategory().equals(p.getCategory()))
                        .filter(product -> Math.abs(product.getPrice().doubleValue() - p.getPrice().doubleValue()) <= 100)
                        .limit(3)
                        .toList())
                .orElseGet(List::of);

        long executionTime = System.currentTimeMillis() - startTime;
        return new CacheResult<>(data, false, executionTime);
    }

    @Override
    public CacheResult<List<Product>> searchWithFilters(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Ejecutando consulta cara con múltiples filtros...");
        long startTime = System.currentTimeMillis();

        this.simulateExpensiveQuery();

        List<Product> data = this.database.stream()
                .filter(product -> product.getCategory().equalsIgnoreCase(category))
                .filter(product -> product.getPrice().compareTo(minPrice) >= 0)
                .filter(product -> product.getPrice().compareTo(maxPrice) <= 0)
                .filter(Product::isAvailable)
                .toList();

        long executionTime = System.currentTimeMillis() - startTime;
        return new CacheResult<>(data, false, executionTime);
    }

    private Optional<Product> findProductById(Long productId) {
        return this.database.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst();
    }

    private void simulateExpensiveQuery() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
