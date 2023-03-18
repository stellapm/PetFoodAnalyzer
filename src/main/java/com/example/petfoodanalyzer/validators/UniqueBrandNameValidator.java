package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.services.products.BrandService;
import com.example.petfoodanalyzer.validators.annotations.UniqueBrandName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueBrandNameValidator implements ConstraintValidator<UniqueBrandName, String> {
    private final BrandService brandService;

    public UniqueBrandNameValidator(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            this.brandService.findByName(value);
            return false;
        } catch (ObjectNotFoundException e){
            return true;
        }
    }
}
