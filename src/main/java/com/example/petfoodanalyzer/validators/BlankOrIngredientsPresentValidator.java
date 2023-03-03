package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.services.ingredients.IngredientService;
import com.example.petfoodanalyzer.validators.annotations.BlankOrIngredientsPresent;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BlankOrIngredientsPresentValidator implements ConstraintValidator<BlankOrIngredientsPresent, String> {
    private final IngredientService ingredientService;

    public BlankOrIngredientsPresentValidator(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.length() == 0) {
            return true;
        }

        return this.ingredientService.allIngredientsPresent(value);
    }
}
