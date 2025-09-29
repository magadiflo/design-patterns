package dev.magadiflo.patterns.plainjava.structural.proxy.cache;

import dev.magadiflo.patterns.plainjava.structural.proxy.cache.model.Product;
import dev.magadiflo.patterns.plainjava.structural.proxy.cache.proxy.ProductServiceCacheProxy;
import dev.magadiflo.patterns.plainjava.structural.proxy.cache.realsubject.ProductServiceImpl;
import dev.magadiflo.patterns.plainjava.structural.proxy.cache.subject.ProductService;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class Client {
    public static void main(String[] args) {
        ProductService realService = new ProductServiceImpl();
        ProductService serviceWithCacheProxy = new ProductServiceCacheProxy(realService, 2);

        // TEST 1: Cache Miss vs Cache Hit
        log.info("Primera llamada (Cache MISS)");
        List<Product> products1 = serviceWithCacheProxy.findByCategory("Electronics", 1);
        log.info("Productos encontrados: {}", products1.size());

        log.info("Segunda llamada inmediata (Cache HIT):");
        List<Product> products2 = serviceWithCacheProxy.findByCategory("Electronics", 1);
        log.info("Productos encontrados: {}", products2.size());
        log.info("");


        // TEST 2: Diferentes operaciones
        log.info("Productos Relacionados (Cache MISS)");
        List<Product> related1 = serviceWithCacheProxy.getRelatedProducts(1L);
        log.info("Productos relacionados: {}", related1.size());

        log.info("Mismos Productos Relacionados (Cache HIT)");
        List<Product> related2 = serviceWithCacheProxy.getRelatedProducts(1L);
        log.info("Productos relacionados: {}", related2.size());
        log.info("");


        // TEST 3: Filtros complejos
        log.info("Búsqueda con filtros (Cache MISS)");
        List<Product> filtered1 = serviceWithCacheProxy.searchWithFilters("Electronics", new BigDecimal("100"), new BigDecimal("1500"));
        log.info("Productos filtrados: {}", filtered1.size());

        log.info("Búsqueda con filtros (Cache HIT)");
        List<Product> filtered2 = serviceWithCacheProxy.searchWithFilters("Electronics", new BigDecimal("100"), new BigDecimal("1500"));
        log.info("Productos filtrados: {}", filtered2.size());
    }
}
