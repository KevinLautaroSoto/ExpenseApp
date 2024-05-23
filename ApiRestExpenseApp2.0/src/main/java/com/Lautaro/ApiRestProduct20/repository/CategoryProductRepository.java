package com.Lautaro.ApiRestProduct20.repository;

import com.Lautaro.ApiRestProduct20.models.CategoryProduct;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryProductRepository {
    ResponseEntity<String> createCategoryProduct(CategoryProduct categoryProduct);
    List<CategoryProduct> getAllCategoryProduct();
    CategoryProduct getCategoryProductById(Long id);
    ResponseEntity<String> updateCategoryProduct(Long id, CategoryProduct NewCategoryProduct);
    ResponseEntity<String> deleteCategoryProduct(Long id);
}
