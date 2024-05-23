package com.Lautaro.ApiRestProduct20.controller;

import com.Lautaro.ApiRestProduct20.models.CategoryProduct;
import com.Lautaro.ApiRestProduct20.service.CategoryProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-product")
public class CategoryProductController {

    private final CategoryProductService categoryProductService;

    @Autowired
    public CategoryProductController(CategoryProductService categoryProductService) {
        this.categoryProductService = categoryProductService;
    }

    @PostMapping
    public ResponseEntity<String> createCategoryProduct (@RequestBody CategoryProduct categoryProduct) {
        return categoryProductService.createCategoryProduct(categoryProduct);
    }

    @GetMapping
    public List<CategoryProduct> getAllCategoryProduct () {
        return categoryProductService.getAllCategoryProduct();
    }

    @GetMapping("/{id}")
    public CategoryProduct getCategoryProductById (@PathVariable Long id) {
        return categoryProductService.getCategoryProductById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategoryProduct (@PathVariable Long id,
                                                         @RequestBody CategoryProduct categoryProduct) {
        return categoryProductService.updateCategoryProduct(id, categoryProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryProduct (@PathVariable Long id) {
        return categoryProductService.deleteCategoryProduct(id);
    }
}
