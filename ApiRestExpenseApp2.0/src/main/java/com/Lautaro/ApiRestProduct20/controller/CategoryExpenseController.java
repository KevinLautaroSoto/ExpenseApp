package com.Lautaro.ApiRestProduct20.controller;

import com.Lautaro.ApiRestProduct20.models.CategoryExpense;
import com.Lautaro.ApiRestProduct20.service.CategoryExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-expense")
public class CategoryExpenseController {

    private final CategoryExpenseService categoryExpenseService;

    @Autowired
    public CategoryExpenseController(CategoryExpenseService categoryExpenseService) {
        this.categoryExpenseService = categoryExpenseService;
    }

    @PostMapping
    public ResponseEntity<String> createCategoryExpense(@RequestBody CategoryExpense categoryExpense){
        return categoryExpenseService.createCategoryExpense(categoryExpense);
    }

    @GetMapping
    public List<CategoryExpense> getAllCategoryExpense() {
        return categoryExpenseService.getAllCategoryExpense();
    }

    @GetMapping("/{id}")
    public CategoryExpense getCategoryExpenseById(@PathVariable Long id) {
        return categoryExpenseService.getCategoryExpenseById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategoryExpense(@PathVariable Long id,
                                                        @RequestBody CategoryExpense categoryExpense) {
        return categoryExpenseService.updateCategoryExpense(id, categoryExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryExpense(@PathVariable Long id) {
        return categoryExpenseService.deleteCategoryExpense(id);
    }
}
