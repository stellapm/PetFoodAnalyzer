package com.example.petfoodanalyzer.models.dtos.products;

import com.example.petfoodanalyzer.validators.annotations.BlankOrIngredientsPresent;
import com.example.petfoodanalyzer.validators.annotations.BlankOrPattern;
import com.example.petfoodanalyzer.validators.annotations.IngredientsPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jdk.jfr.DataAmount;


public class EditProductDTO {
    @BlankOrPattern(regexp = "[\\w\\W]{3,20}", message = "Name should be between 3 and 20 characters")
    private String name;

    @BlankOrPattern(regexp = "[\\w\\W]{5}", message = "Description should be at least 5 characters")
    private String description;

    private String picUrl;

    @NotBlank(message = "Please select a brand.")
    private String brandStr;

    @NotBlank(message = "Please select a pet.")
    private String petStr;

    @BlankOrPattern(regexp = "^([a-zA-Z0-9]+,?\\s*)+$", message = "Please enter ingredients separated by commas")
    @BlankOrIngredientsPresent
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
