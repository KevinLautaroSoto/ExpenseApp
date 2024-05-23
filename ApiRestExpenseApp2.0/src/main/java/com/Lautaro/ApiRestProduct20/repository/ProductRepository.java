package com.Lautaro.ApiRestProduct20.repository;

import com.Lautaro.ApiRestProduct20.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductRepository {
    ResponseEntity<String> createProduct(Product product);
    List<Product> getAllProduct();
    Product getProductById(Long id);
    ResponseEntity<String> updateProduct(Long id, Product product);
    ResponseEntity<String> deleteProduct(Long id);
}
