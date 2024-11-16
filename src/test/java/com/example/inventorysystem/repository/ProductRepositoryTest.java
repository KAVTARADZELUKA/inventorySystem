package com.example.inventorysystem.repository;

import com.example.inventorysystem.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldSaveProduct() {
        Product product = new Product("Product A", "Description A", 100.0, 10);
        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedProduct.getName()).isEqualTo("Product A");
    }

    @Test
    void shouldFindProductById() {
        Product product = new Product("Product B", "Description B", 200.0, 20);
        Product savedProduct = productRepository.save(product);

        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getName()).isEqualTo("Product B");
    }

    @Test
    void shouldDeleteProduct() {
        Product product = new Product("Product C", "Description C", 300.0, 30);
        Product savedProduct = productRepository.save(product);

        productRepository.deleteById(savedProduct.getId());
        Optional<Product> deletedProduct = productRepository.findById(savedProduct.getId());

        assertThat(deletedProduct).isEmpty();
    }
}
