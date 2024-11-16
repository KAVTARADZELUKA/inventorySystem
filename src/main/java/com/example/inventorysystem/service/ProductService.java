package com.example.inventorysystem.service;

import com.example.inventorysystem.dto.ProductRequestDto;
import com.example.inventorysystem.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto addProduct(ProductRequestDto productRequestDto);
    ProductResponseDto updateProduct(Long id, ProductRequestDto productRequestDto);
}