package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.services.products.IngredientService;
import com.example.petfoodanalyzer.validators.annotations.UniqueIngredientName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueIngredientNameValidator implements ConstraintValidator<UniqueIngredientName, String> {
    private final IngredientService ingredientService;

    public UniqueIngredientNameValidator(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.ingredientService.findByName(value) == null;
    }
}
