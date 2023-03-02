package com.example.petfoodanalyzer.models.dtos.products;

import com.example.petfoodanalyzer.validators.annotations.IngredientsPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EditProductDTO {
    @Size(min = 3, max = 20, message = "Name should be between 3 and 20 characters")
    @NotBlank(message = "Please enter name.")
    private String name;

    @NotBlank(message = "Please enter description.")
    @Size(min = 5, message = "Description should be at least 5 characters")
    private String description;

    @NotBlank(message = "Please enter a valid URL.")
    private String picUrl;

    @NotBlank(message = "Please select a brand.")
    private String brandStr;

    @NotBlank(message = "Please select a pet.")
    private String petStr;

    @Pattern(regexp = "^([a-zA-Z0-9]+,?\\s*)+$", message = "Please enter ingredients separated by commas")
    @NotBlank(message = "Please enter ingredients.")
    @IngredientsPresent
    private String ingredientsList;

    public EditProductDTO() {
    }
    public String getName() {
        return name;
    }

    public EditProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditProductDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public EditProductDTO setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getBrandStr() {
        return brandStr;
    }

    public EditProductDTO setBrandStr(String brandStr) {
        this.brandStr = brandStr;
        return this;
    }

    public String getPetStr() {
        return petStr;
    }

    public EditProductDTO setPetStr(String petStr) {
        this.petStr = petStr;
        return this;
    }

    public String getIngredientsList() {
        return ingredientsList;
    }

    public EditProductDTO setIngredientsList(String ingredientsList) {
        this.ingredientsList = ingredientsList;
        return this;
    }


}
