package com.Lautaro.ApiRestProduct20.service;

import com.Lautaro.ApiRestProduct20.models.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseService(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public ResponseEntity<String> createExpense(Expense expense) {
        return expenseService.createExpense(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    public Expense getExpenseById(Long id) {
        return expenseService.getExpenseById(id);
    }

    public ResponseEntity<String> updateExpense(Long id, Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    public ResponseEntity<String> deleteExpense(Long id) {
        return expenseService.deleteExpense(id);
    }
}
