package com.Lautaro.ApiRestProduct20.repository.impl;

import com.Lautaro.ApiRestProduct20.models.CategoryExpense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CategoryExpenseRepositoryImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CategoryExpenseRepositoryImpl repository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategoryExpense() {
        // GIVEN
        CategoryExpense categoryExpense = new CategoryExpense();
        categoryExpense.setName("Test Category");

        // WHEN
        ResponseEntity<String> response = repository.createCategoryExpense(categoryExpense);

        // THEN
        assertEquals(ResponseEntity.ok("CategoryExpense successfully created into the database."), response);
        verify(jdbcTemplate, times(1)).update(anyString(), any(Object[].class));
    }

    @Test
    @DisplayName("Test if getAllCategoryExpense return all the category expenses saved in the dastabase.")
    public void testGetAllCategoryExpense() {
        //Given
        CategoryExpense categoryExpense = new CategoryExpense();
        categoryExpense.setId(1L);
        categoryExpense.setName("Test Category");

        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(List.of(categoryExpense));

        //When
        List<CategoryExpense> result = repository.getAllCategoryExpense();

        //Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Category", result.get(0).getName());
    }

    /*
    @Test
    @DisplayName("Test if GetExpenseCategoryById return the correct category.")
    public void testGetCategoryExpenseById() {
        // Given
        CategoryExpense categoryExpense = new CategoryExpense();
        categoryExpense.setId(1L);
        categoryExpense.setName("Test Category");

        when(jdbcTemplate.queryForObject(anyString(),
                any(Object[].class),
                any(RowMapper.class)))
                .thenReturn(categoryExpense);

        // When
        CategoryExpense result = repository.getCategoryExpenseById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test Category", result.getName());
    }
     */
}
