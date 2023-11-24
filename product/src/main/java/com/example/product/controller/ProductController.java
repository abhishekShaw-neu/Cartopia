package com.example.product.controller;

import com.example.product.application.dto.ProductRequestDTO;
import com.example.product.application.dto.ProductResponseDTO;
import com.example.product.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductResponseDTO>> listAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable("productId") int id) {
        return productService.getProductById(id);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("productId") int productId, @RequestBody ProductRequestDTO productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable("productId") int productId) {
        return productService.deleteProduct(productId);
    }

}
