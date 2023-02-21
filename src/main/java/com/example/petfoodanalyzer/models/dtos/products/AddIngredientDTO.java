package com.example.petfoodanalyzer.models.dtos.products;

import com.example.petfoodanalyzer.validators.annotations.UniqueIngredientName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddIngredientDTO {
    @NotBlank(message = "Please select a category")
    private String ingredientCategoryStr;

    @Size(min = 3, max = 30, message = "Name should be between 3 and 20 characters!")
    @UniqueIngredientName
    private String name;

    @Size(min = 2, message = "Description should be at least 2 characters.")
    private String description;

    public AddIngredientDTO() {
    }

    public String getIngredientCategoryStr() {
        return ingredientCategoryStr;
    }

    public AddIngredientDTO setIngredientCategoryStr(String ingredientCategoryStr) {
        this.ingredientCategoryStr = ingredientCategoryStr;
        return this;
    }

    public String getName() {
        return name;
    }

    public AddIngredientDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddIngredientDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
