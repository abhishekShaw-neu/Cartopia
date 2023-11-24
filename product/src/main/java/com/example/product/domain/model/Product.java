package com.example.product.domain.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private float price;

    @Column(name = "category")
    private Integer category;

    @Column(name = "stock_quantity")
    private int stockQuantity;

    @Column(name = "available")
    private String available;
}
