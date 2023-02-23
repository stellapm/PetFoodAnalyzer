package com.example.petfoodanalyzer.models.dtos.ingredients;

import com.example.petfoodanalyzer.validators.annotations.IngredientsPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class IngredientsListDTO {
    @Pattern(regexp = "^([a-zA-Z0-9]+,?\\s*)+$", message = "Please enter ingredients separated by commas")
    @NotBlank(message = "Please enter ingredients.")
    @IngredientsPresent
    private String ingredientsList;

    public IngredientsListDTO() {
    }

    public String getIngredientsList() {
        return ingredientsList;
    }

    public IngredientsListDTO setIngredientsList(String ingredientsList) {
        this.ingredientsList = ingredientsList;
        return this;
    }
}
