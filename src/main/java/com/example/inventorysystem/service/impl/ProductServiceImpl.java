package com.example.inventorysystem.service.impl;

import com.example.inventorysystem.dto.ProductRequestDto;
import com.example.inventorysystem.dto.ProductResponseDto;
import com.example.inventorysystem.entity.Product;
import com.example.inventorysystem.exception.ResourceNotFoundException;
import com.example.inventorysystem.repository.ProductRepository;
import com.example.inventorysystem.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public ProductResponseDto addProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        mapToEntity(productRequestDto, product);
        Product savedProduct = productRepository.save(product);
        return mapToResponseDto(savedProduct);
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        mapToEntity(productRequestDto, product);
        productRepository.save(product);
        return mapToResponseDto(product);
    }

    private ProductResponseDto mapToResponseDto(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(),
                product.getDescription(), product.getPrice(), product.getQuantity());
    }

    private void mapToEntity(ProductRequestDto requestDto, Product product) {
        product.setName(requestDto.getName());
        product.setDescription(requestDto.getDescription());
        product.setPrice(requestDto.getPrice());
        product.setQuantity(requestDto.getQuantity());
    }
}
