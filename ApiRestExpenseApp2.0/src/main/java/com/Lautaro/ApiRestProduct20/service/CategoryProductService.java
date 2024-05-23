package com.Lautaro.ApiRestProduct20.service;

import com.Lautaro.ApiRestProduct20.models.CategoryProduct;
import com.Lautaro.ApiRestProduct20.repository.CategoryProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryProductService {

    private final CategoryProductRepository categoryProductRepository;

    @Autowired
    public CategoryProductService(CategoryProductRepository categoryProductRepository) {
        this.categoryProductRepository = categoryProductRepository;
    }

    public ResponseEntity<String> createCategoryProduct (CategoryProduct categoryProduct) {
        return categoryProductRepository.createCategoryProduct(categoryProduct);
    }

    public List<CategoryProduct> getAllCategoryProduct(){
        return categoryProductRepository.getAllCategoryProduct();
    }

    public CategoryProduct getCategoryProductById(Long id) {
        return categoryProductRepository.getCategoryProductById(id);
    }

    public ResponseEntity<String> updateCategoryProduct(Long id, CategoryProduct categoryProduct) {
        return categoryProductRepository.updateCategoryProduct(id, categoryProduct);
    }

    public ResponseEntity<String> deleteCategoryProduct(Long id) {
        return categoryProductRepository.deleteCategoryProduct(id);
    }
}
