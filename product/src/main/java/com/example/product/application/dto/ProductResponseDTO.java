package com.example.product.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
    private String errorMessage;
    private int id;
    private String name;
    private String description;
    private float price;
    private CategoryDTO category;
    private int stockQuantity;
    private String available;

    public ProductResponseDTO() {
        // Initialize any necessary fields or perform setup if needed
    }
    public ProductResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }





}
