package com.Lautaro.ApiRestProduct20.service;


import com.Lautaro.ApiRestProduct20.models.CategoryExpense;
import com.Lautaro.ApiRestProduct20.repository.CategoryExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryExpenseService {

    private final CategoryExpenseRepository categoryExpenseRepository;

    @Autowired
    public CategoryExpenseService(CategoryExpenseRepository categoryExpenseRepository) {
        this.categoryExpenseRepository = categoryExpenseRepository;
    }

    public ResponseEntity<String> createCategoryExpense(CategoryExpense categoryExpense) {
        return categoryExpenseRepository.createCategoryExpense(categoryExpense);
    }

    public List<CategoryExpense> getAllCategoryExpense() {
        return categoryExpenseRepository.getAllCategoryExpense();
    }

    public CategoryExpense getCategoryExpenseById(Long id) {
        return categoryExpenseRepository.getCategoryExpenseById(id);
    }

    public ResponseEntity<String> updateCategoryExpense(Long id, CategoryExpense newCategoryExpense){
        return categoryExpenseRepository.updateCategoryExpense(id, newCategoryExpense);
    }

    public ResponseEntity<String> deleteCategoryExpense(Long id) {
        return categoryExpenseRepository.deleteCategoryExpense(id);
    }
}
