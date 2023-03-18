package com.example.petfoodanalyzer.validators;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.services.ingredients.IngredientService;
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
        try {
            this.ingredientService.findByName(value);
            return false;
        } catch (ObjectNotFoundException e){
            return true;
        }
    }
}
