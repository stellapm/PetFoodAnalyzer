package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.services.products.ProductService;
import com.example.petfoodanalyzer.validators.annotations.UniqueProductName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueProductNameValidator implements ConstraintValidator<UniqueProductName, String> {
    private final ProductService productService;

    @Autowired
    public UniqueProductNameValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.productService.findByName(value) == null;
    }
}
