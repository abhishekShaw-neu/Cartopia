package com.example.product.domain.service;

import com.example.product.application.dto.CategoryDTO;
import com.example.product.application.dto.ProductRequestDTO;
import com.example.product.application.dto.ProductResponseDTO;
import com.example.product.domain.model.Category;
import com.example.product.domain.model.Product;
import com.example.product.domain.repository.CategoryRepository;
import com.example.product.domain.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private static final String ACTION_1 = "Product not found";

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public ResponseEntity<ProductResponseDTO> createProduct(ProductRequestDTO productRequestDTO) {

        try {

            Category category = categoryRepository.findById(productRequestDTO.getCategory()).orElse(null);

            if (category != null) {
                Product product = modelMapper.map(productRequestDTO, Product.class);
                Product savedProduct = productRepository.save(product);
                CategoryDTO categoryDTO = modelMapper.map(category, CategoryDTO.class);
                ProductResponseDTO productResponseDTO = modelMapper.map(savedProduct, ProductResponseDTO.class);
                productResponseDTO.setCategory(categoryDTO);
                return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDTO);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponseDTO("Category not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponseDTO("Error creating product: " + e.getMessage()));
        }
    }

    public ResponseEntity<ProductResponseDTO> updateProduct(Integer productId, ProductRequestDTO updatedProductRequest) {
        try {
            // Check if the product exists
            Optional<Product> existingProductOptional = productRepository.findById(productId);

            if (existingProductOptional.isPresent()) {
                Product existingProduct = existingProductOptional.get();

                // Validate updatedProductRequest and perform necessary business logic

                // Update fields of the existing product with the new values
                modelMapper.map(updatedProductRequest, existingProduct);

                // Retrieve the full Category object from the database based on categoryId in the request
                Category category = categoryRepository.findById(updatedProductRequest.getCategory()).orElse(null);

                if (category != null) {
                    // Update the category of the product
                    existingProduct.setCategory(category.getCategoryId());

                    // Save the updated product to the database
                    Product updatedProduct = productRepository.save(existingProduct);

                    // Map the updated product to ProductResponseDTO with the full CategoryDTO object
                    ProductResponseDTO responseDTO = modelMapper.map(updatedProduct, ProductResponseDTO.class);
                    responseDTO.setCategory(modelMapper.map(category, CategoryDTO.class));

                    return ResponseEntity.ok(responseDTO);
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ProductResponseDTO("Category not found"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductResponseDTO(ACTION_1));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ProductResponseDTO("Error updating product: " + e.getMessage()));
        }
    }

    public ResponseEntity<ProductResponseDTO> getProductById(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductResponseDTO responseDTO = modelMapper.map(product, ProductResponseDTO.class);

            // Check if product has a valid category before mapping
            if (product.getCategory() != null) {
                // Assuming product.getCategory() returns the category ID
                Category category = categoryRepository.findById(product.getCategory()).orElse(null);

                if (category != null) {
                    responseDTO.setCategory(modelMapper.map(category, CategoryDTO.class));
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ProductResponseDTO("Error mapping category for product."));
                }
            }

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ProductResponseDTO(ACTION_1));
        }
    }



    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        try {
            List<Product> allProducts = productRepository.findAll();

            if (!allProducts.isEmpty()) {
                List<ProductResponseDTO> responseDTOs = allProducts.stream().map(product -> {
                    ProductResponseDTO responseDTO = modelMapper.map(product, ProductResponseDTO.class);
                    Category category = categoryRepository.findById(product.getCategory()).orElse(null);
                    responseDTO.setCategory(modelMapper.map(category, CategoryDTO.class));
                    return responseDTO;
                }).collect(Collectors.toList());

                return ResponseEntity.ok(responseDTOs);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList(new ProductResponseDTO(ACTION_1)));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(new ProductResponseDTO("Error retrieving products: " + e.getMessage())));
        }
    }

    public ResponseEntity<String> deleteProduct(int productId) {
        try {
            Optional<Product> productOptional = productRepository.findById(productId);

            if (productOptional.isPresent()) {
                productRepository.deleteById(productId);
                return ResponseEntity.ok("Product deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ACTION_1);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting product: " + e.getMessage());
        }
    }


}
