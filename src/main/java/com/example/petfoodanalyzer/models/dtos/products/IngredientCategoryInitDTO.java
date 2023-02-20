package com.example.petfoodanalyzer.models.dtos.products;

public class IngredientCategoryInitDTO {
    private String code;

    private String description;

    public IngredientCategoryInitDTO() {
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public IngredientCategoryInitDTO setCode(String code) {
        this.code = code;
        return this;
    }

    public IngredientCategoryInitDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
