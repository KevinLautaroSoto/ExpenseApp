package com.Lautaro.ApiRestProduct20.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private double totalAmount;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_expense_id")
    private CategoryExpense categoryExpense;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, orphanRemoval = true)// *1
    private List<Product> products;


    public Expense(){}

    public Expense(String description, double totalAmount, List<Product> products, Date date, User user,
                   CategoryExpense categoryExpense) {
        this.description = description;
        this.totalAmount = totalAmount;
        this.products = products;
        this.date = date;
        this.user = user;
        this.categoryExpense = categoryExpense;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CategoryExpense getCategoryExpense() {
        return categoryExpense;
    }

    public void setCategoryExpense(CategoryExpense categoryExpense) {
        this.categoryExpense = categoryExpense;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", totalAmount=" + totalAmount +
                ", products=" + products +
                ", date=" + date +
                ", user=" + user +
                '}';
    }
}

/*
NOTAS:
*1: El atributo orphanRemoval = true en una anotación de relación JPA (@OneToMany, @OneToOne, etc.) indica que los hijos
 (entidades relacionadas) deben eliminarse automáticamente cuando se eliminan de la colección de la entidad padre.
 Es útil para mantener la integridad de la base de datos sin tener que gestionar manualmente la eliminación de las
 entidades huérfanas. Aquí hay un ejemplo para entender mejor:
 si en una clase padre almacenas una lista de productos que sera la entidad hija. cuando elimines de dicha lista un
 producto, esta autimaticamente se eliminara tambien de la base de datos como entidad propiamente.
 */