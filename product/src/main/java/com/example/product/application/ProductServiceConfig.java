package com.example.product.application;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductServiceConfig {

    // Add configuration beans or properties here if needed

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
