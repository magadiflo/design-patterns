package dev.magadiflo.patterns.plainjava.structural.proxy.cache.proxy;

import dev.magadiflo.patterns.plainjava.structural.proxy.cache.cache.CacheEntry;
import dev.magadiflo.patterns.plainjava.structural.proxy.cache.model.Product;
import dev.magadiflo.patterns.plainjava.structural.proxy.cache.subject.ProductService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Slf4j
public class ProductServiceCacheProxy implements ProductService {

    private final ProductService realService;
    private final ConcurrentHashMap<String, CacheEntry<List<Product>>> cache;
    private final int ttlMinutes;

    public ProductServiceCacheProxy(ProductService realService, int ttlMinutes) {
        this.realService = realService;
        this.cache = new ConcurrentHashMap<>();
        this.ttlMinutes = ttlMinutes;
    }

    @Override
    public List<Product> findByCategory(String category, int page) {
        String cacheKey = this.generateKey("category", category, "page", String.valueOf(page));
        return this.executeWithCache(cacheKey, () -> this.realService.findByCategory(category, page));
    }

    @Override
    public List<Product> getRelatedProducts(Long productId) {
        String cacheKey = this.generateKey("related", String.valueOf(productId));
        return this.executeWithCache(cacheKey, () -> this.realService.getRelatedProducts(productId));
    }

    @Override
    public List<Product> searchWithFilters(String category, BigDecimal minPrice, BigDecimal maxPrice) {
        String cacheKey = this.generateKey("filters", category, minPrice.toString(), maxPrice.toString());
        return this.executeWithCache(cacheKey, () -> this.realService.searchWithFilters(category, minPrice, maxPrice));
    }

    // ===============================================
    // CACHE-ASIDE IMPLEMENTATION
    // ===============================================
    private List<Product> executeWithCache(String cacheKey, Supplier<List<Product>> operation) {
        long startTime = System.currentTimeMillis();

        //1. Verificar si existe en caché y no ha expirado
        CacheEntry<List<Product>> entry = this.cache.get(cacheKey);

        if (entry != null && !entry.isExpired()) {
            long duration = System.currentTimeMillis() - startTime;
            log.info("Cache HIT. Datos recuperados desde caché en {} ms", duration);
            log.info("Datos almacenados en caché desde: {}", entry.getTimestamp());
            return entry.getData();
        }

        //2. Si no existe o ha expirado, llamamos al servicio real
        log.info("Cache MISS. Consultando el servicio real");
        List<Product> result = operation.get();

        //3. Guardando el resultado en caché
        this.cache.put(cacheKey, new CacheEntry<>(result, this.ttlMinutes));

        long duration = System.currentTimeMillis() - startTime;
        log.info("Resultado guardado en caché. Tiempo total: {} ms", duration);

        return result;
    }

    private String generateKey(String... parts) {
        return String.join(":", parts);
    }
}
