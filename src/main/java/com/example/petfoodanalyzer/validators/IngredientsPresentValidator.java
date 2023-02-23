package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.services.ingredients.IngredientService;
import com.example.petfoodanalyzer.validators.annotations.IngredientsPresent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IngredientsPresentValidator implements ConstraintValidator<IngredientsPresent, String> {
    private final IngredientService ingredientService;

    public IngredientsPresentValidator(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return this.ingredientService.allIngredientsPresent(value);
    }
}
