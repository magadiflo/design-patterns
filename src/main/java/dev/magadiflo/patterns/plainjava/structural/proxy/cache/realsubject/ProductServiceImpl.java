package dev.magadiflo.patterns.plainjava.structural.proxy.cache.realsubject;

import dev.magadiflo.patterns.plainjava.structural.proxy.cache.mockdata.MockProductDatabase;
import dev.magadiflo.patterns.plainjava.structural.proxy.cache.model.Product;
import dev.magadiflo.patterns.plainjava.structural.proxy.cache.subject.ProductService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ProductServiceImpl implements ProductService {

    private final List<Product> database = MockProductDatabase.initializeDatabase();

    @Override
    public List<Product> findByCategory(String category, int page) {
        log.info("Ejecutando consulta cara por categoría...");
        this.simulateExpensiveQuery();

        int pageSize = 5;
        int startIndex = (page - 1) * pageSize;

        return this.database.stream()
                .filter(product -> product.category().equalsIgnoreCase(category) && product.available())
                .skip(startIndex)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> getRelatedProducts(Long productId) {
        log.info("Ejecutando consulta cara para productos relacionados...");
        this.simulateExpensiveQuery();

        Optional<Product> optionalProduct = this.findById(productId);
        if (optionalProduct.isEmpty()) {
            return Collections.emptyList();
        }

        // Simulamos un algoritmo complejo para encontrar productos relacionados
        return this.database.stream()
                .filter(product -> !product.id().equals(productId))
                .filter(product -> product.category().equals(optionalProduct.get().category()))
                .filter(product -> Math.abs(product.price().doubleValue() - optionalProduct.get().price().doubleValue()) <= 100)
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchWithFilters(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("Ejecutando consulta cara con múltiples filtros...");
        this.simulateExpensiveQuery();

        return this.database.stream()
                .filter(product -> product.category().equalsIgnoreCase(category))
                .filter(product -> product.price().compareTo(minPrice) >= 0)
                .filter(product -> product.price().compareTo(maxPrice) <= 0)
                .filter(Product::available)
                .collect(Collectors.toList());
    }

    private Optional<Product> findById(Long productId) {
        return this.database.stream()
                .filter(product -> product.id().equals(productId))
                .findFirst();
    }

    private void simulateExpensiveQuery() {
        try {
            Thread.sleep(Duration.ofSeconds(3));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
