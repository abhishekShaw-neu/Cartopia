package com.example.product.application.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

    private int id;
    private String name;
    private String description;
    private float price;
    private int category;
    private int stockQuantity;
    private String available;
}
