package com.example.Spring_REST_Service.controller;

import com.example.Spring_REST_Service.controller.payload.NewProductPayload;
import com.example.Spring_REST_Service.entity.Product;
import com.example.Spring_REST_Service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("catalogue-api/products")
public class ProductsRestController {

    private final ProductService productService;

    @GetMapping
    public List<Product> findProducts() {
        return this.productService.findAllProducts();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody NewProductPayload payload,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        Product product = this.productService.createProduct(payload.title(), payload.details());
        return ResponseEntity
                .created(uriComponentsBuilder
                        .replacePath("catalogue-api/products/{productId}")
                        .build(Map.of("productId", product.getId())))
                .body(product);
    }
}
