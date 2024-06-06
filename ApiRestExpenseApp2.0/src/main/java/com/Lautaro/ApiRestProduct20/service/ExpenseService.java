package com.Lautaro.ApiRestProduct20.service;

import com.Lautaro.ApiRestProduct20.models.Expense;
import com.Lautaro.ApiRestProduct20.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public ResponseEntity<String> createExpense(Expense expense) {
        return expenseRepository.createExpense(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.getAllExpense();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.getExpenseById(id);
    }

    public ResponseEntity<String> updateExpense(Long id, Expense expense) {return expenseRepository.updateExpense(id, expense);}

    public ResponseEntity<String> deleteExpense(Long id) {
        return expenseRepository.deleteExpense(id);
    }
}
