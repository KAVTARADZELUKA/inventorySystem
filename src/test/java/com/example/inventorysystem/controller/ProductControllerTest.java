package com.example.inventorysystem.controller;

import com.example.inventorysystem.dto.ProductRequestDto;
import com.example.inventorysystem.dto.ProductResponseDto;
import com.example.inventorysystem.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void shouldGetAllProducts() throws Exception {
        ProductResponseDto product1 = new ProductResponseDto(1L, "Product A", "Description A", 100.0, 10);
        ProductResponseDto product2 = new ProductResponseDto(2L, "Product B", "Description B", 200.0, 20);

        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void shouldAddProduct() throws Exception {
        ProductRequestDto requestDto = new ProductRequestDto("Product A", "Description A", 100.0, 10);
        ProductResponseDto responseDto = new ProductResponseDto(1L, "Product A", "Description A", 100.0, 10);

        when(productService.addProduct(any(ProductRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Product A\", \"description\": \"Description A\", \"price\": 100.0, \"quantity\": 10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Product A"));

        verify(productService, times(1)).addProduct(any(ProductRequestDto.class));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductRequestDto requestDto = new ProductRequestDto("Updated Product", "Updated Description", 150.0, 15);
        ProductResponseDto responseDto = new ProductResponseDto(1L, "Updated Product", "Updated Description", 150.0, 15);

        when(productService.updateProduct(eq(1L), any(ProductRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Product\", \"description\": \"Updated Description\", \"price\": 150.0, \"quantity\": 15}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Product"));

        verify(productService, times(1)).updateProduct(eq(1L), any(ProductRequestDto.class));
    }
}
