package com.Lautaro.ApiRestProduct20.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "CategoryProduct")
public class CategoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "categoryProduct")
    private List<Product> products;

    public CategoryProduct() {}

    public CategoryProduct(String name) {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "CategoryProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
