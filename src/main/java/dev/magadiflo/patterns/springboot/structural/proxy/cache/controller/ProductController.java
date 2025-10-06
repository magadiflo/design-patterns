package dev.magadiflo.patterns.springboot.structural.proxy.cache.controller;

import dev.magadiflo.patterns.springboot.structural.proxy.cache.cache.CacheResult;
import dev.magadiflo.patterns.springboot.structural.proxy.cache.dto.ApiResponse;
import dev.magadiflo.patterns.springboot.structural.proxy.cache.dto.ProductDTO;
import dev.magadiflo.patterns.springboot.structural.proxy.cache.model.Product;
import dev.magadiflo.patterns.springboot.structural.proxy.cache.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/products/cache")
public class ProductController {

    private final ProductService productService;


    public ProductController(@Qualifier("productServiceCacheProxy") ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(path = "/category/{category}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> findByCategory(@PathVariable String category) {
        return this.response(this.productService.findByCategory(category, 1));
    }

    @GetMapping(path = "/related/{productId}")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getRelatedProducts(@PathVariable Long productId) {
        return this.response(this.productService.getRelatedProducts(productId));
    }

    @GetMapping(path = "/filter")
    public ResponseEntity<ApiResponse<List<ProductDTO>>> getRelatedProducts(@RequestParam String category,
                                                                            @RequestParam BigDecimal minPrice,
                                                                            @RequestParam BigDecimal maxPrice) {
        return this.response(this.productService.searchWithFilters(category, minPrice, maxPrice));
    }

    private ResponseEntity<ApiResponse<List<ProductDTO>>> response(CacheResult<List<Product>> result) {
        List<ProductDTO> productDTOList = result.data().stream()
                .map(p -> new ProductDTO(p.getId(), p.getName(), p.getCategory(), p.getPrice(), p.getRating(), p.getBrand()))
                .toList();
        var response = ApiResponse.create(true, "Productos recuperados correctamente", productDTOList, result.cacheHit(), result.executionTimeMs());
        return ResponseEntity.ok(response);
    }
}
