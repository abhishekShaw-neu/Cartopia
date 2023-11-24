package com.example.product.domain.repository;

import com.example.product.domain.model.Category;
import com.example.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Integer> {
}