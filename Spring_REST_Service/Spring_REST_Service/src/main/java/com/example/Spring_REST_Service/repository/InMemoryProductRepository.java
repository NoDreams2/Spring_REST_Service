package com.example.Spring_REST_Service.repository;

import com.example.Spring_REST_Service.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Product save(Product product) {
        product.setId(this.products.stream()
                .max(Comparator.comparingInt(Product::getId))
                .map(Product::getId)
                .orElse(0) + 1);
        this.products.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return this.products.stream()
                .filter(product -> Objects.equals(productId, product.getId()))
                .findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        boolean isDeleted = this.products.removeIf(product -> Objects.equals(product.getId(), id));
        if (isDeleted) {
            IntStream.range(0, this.products.size())
                    .forEach(index -> {
                        Product product = this.products.get(index);
                        product.setId(index + 1);
                    });
        }
    }
}
