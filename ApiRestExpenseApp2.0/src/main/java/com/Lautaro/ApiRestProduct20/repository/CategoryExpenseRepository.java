package com.Lautaro.ApiRestProduct20.repository;

import com.Lautaro.ApiRestProduct20.models.CategoryExpense;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryExpenseRepository {
    ResponseEntity<String> createCategoryExpense(CategoryExpense categoryExpense);
    List<CategoryExpense> getAllCategoryExpense();
    CategoryExpense getCategoryExpenseById(Long id);
    ResponseEntity<String> updateCategoryExpense(Long id, CategoryExpense newCategoryExpense);
    ResponseEntity<String> deleteCategoryExpense(Long id);
}
