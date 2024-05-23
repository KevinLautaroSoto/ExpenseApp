package com.Lautaro.ApiRestProduct20.service;

import com.Lautaro.ApiRestProduct20.models.Product;
import com.Lautaro.ApiRestProduct20.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    private ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<String> createProduct(Product product) {
        return productRepository.createProduct(product);
    }

    public List<Product> getAllProduct() {
        return productRepository.getAllProduct();
    }

    public Product getProductById(Long id) {
        return productRepository.getProductById(id);
    }

    public ResponseEntity<String> updateProduct(Long id, Product product) {
        return productRepository.updateProduct(id, product);
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        return productRepository.deleteProduct(id);
    }
}
