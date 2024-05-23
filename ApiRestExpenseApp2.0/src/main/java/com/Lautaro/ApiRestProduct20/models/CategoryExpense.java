package com.Lautaro.ApiRestProduct20.models;

import jakarta.persistence.*;

@Entity
@Table(name = "CategoryExpense")
public class CategoryExpense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public CategoryExpense() {}

    public CategoryExpense(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryExpense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
