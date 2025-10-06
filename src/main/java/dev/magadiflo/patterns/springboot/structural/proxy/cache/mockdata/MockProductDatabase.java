package dev.magadiflo.patterns.springboot.structural.proxy.cache.mockdata;

import dev.magadiflo.patterns.springboot.structural.proxy.cache.model.Product;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MockProductDatabase {
    public static List<Product> initializeDatabase() {
        List<Product> products = new ArrayList<>();

        // Electronics
        products.add(new Product(1L, "iPhone 15", "Electronics", new BigDecimal("1200.00"), 4.5, "Apple", true));
        products.add(new Product(2L, "Samsung Galaxy S24", "Electronics", new BigDecimal("1100.00"), 4.3, "Samsung", true));
        products.add(new Product(3L, "MacBook Pro", "Electronics", new BigDecimal("2500.00"), 4.7, "Apple", true));
        products.add(new Product(4L, "Dell XPS 13", "Electronics", new BigDecimal("1800.00"), 4.4, "Dell", true));
        products.add(new Product(5L, "AirPods Pro", "Electronics", new BigDecimal("250.00"), 4.6, "Apple", true));

        // Clothing
        products.add(new Product(6L, "Nike T-Shirt", "Clothing", new BigDecimal("45.00"), 4.2, "Nike", true));
        products.add(new Product(7L, "Levi's Jeans", "Clothing", new BigDecimal("80.00"), 4.4, "Levi's", true));
        products.add(new Product(8L, "Adidas Sneakers", "Clothing", new BigDecimal("120.00"), 4.3, "Adidas", true));
        products.add(new Product(9L, "North Face Jacket", "Clothing", new BigDecimal("200.00"), 4.5, "North Face", true));
        products.add(new Product(10L, "Casio Watch", "Clothing", new BigDecimal("150.00"), 4.1, "Casio", true));

        // Home
        products.add(new Product(11L, "Dyson Vacuum", "Home", new BigDecimal("400.00"), 4.6, "Dyson", true));
        products.add(new Product(12L, "Nespresso Coffee Machine", "Home", new BigDecimal("180.00"), 4.4, "Nespresso", true));
        products.add(new Product(13L, "Ergonomic Chair", "Home", new BigDecimal("300.00"), 4.2, "Herman Miller", true));
        products.add(new Product(14L, "Dining Table", "Home", new BigDecimal("500.00"), 4.3, "IKEA", true));
        products.add(new Product(15L, "LED Lamp", "Home", new BigDecimal("75.00"), 4.0, "Philips", true));

        return products;
    }
}
