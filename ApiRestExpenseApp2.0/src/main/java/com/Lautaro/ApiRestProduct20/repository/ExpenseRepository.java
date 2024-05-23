package com.Lautaro.ApiRestProduct20.repository;

import com.Lautaro.ApiRestProduct20.models.Expense;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ExpenseRepository {
    ResponseEntity<String> createExpense(Expense expense);
    List<Expense> getAllExpense();
    Expense getExpenseById(Long id);
    ResponseEntity<String> udpateExpense(Long id, Expense newExpense);
    ResponseEntity<String> deleteExpense(Long id);
}
