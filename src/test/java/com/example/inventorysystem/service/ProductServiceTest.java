package com.example.inventorysystem.service;

import com.example.inventorysystem.dto.ProductRequestDto;
import com.example.inventorysystem.dto.ProductResponseDto;
import com.example.inventorysystem.entity.Product;
import com.example.inventorysystem.exception.ResourceNotFoundException;
import com.example.inventorysystem.repository.ProductRepository;
import com.example.inventorysystem.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetAllProducts() {
        Product product1 = new Product("Product A", "Description A", 100.0, 10);
        Product product2 = new Product("Product B", "Description B", 200.0, 20);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<ProductResponseDto> products = productService.getAllProducts();

        assertThat(products).hasSize(2);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldAddProduct() {
        ProductRequestDto requestDto = new ProductRequestDto("Product A", "Description A", 100.0, 10);
        Product product = new Product("Product A", "Description A", 100.0, 10);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductResponseDto responseDto = productService.addProduct(requestDto);

        assertThat(responseDto.getName()).isEqualTo("Product A");
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldUpdateProduct() {
        Product existingProduct = new Product("Product A", "Description A", 100.0, 10);
        Product updatedProduct = new Product("Updated Product", "Updated Description", 150.0, 15);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        ProductRequestDto requestDto = new ProductRequestDto("Updated Product", "Updated Description", 150.0, 15);
        ProductResponseDto responseDto = productService.updateProduct(1L, requestDto);

        assertThat(responseDto.getName()).isEqualTo("Updated Product");
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        ProductRequestDto requestDto = new ProductRequestDto("Product A", "Description A", 100.0, 10);

        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(1L, requestDto));
        verify(productRepository, times(1)).findById(1L);
    }
}
