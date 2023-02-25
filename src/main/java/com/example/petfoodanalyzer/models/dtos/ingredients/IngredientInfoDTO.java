package com.example.petfoodanalyzer.models.dtos.ingredients;

public class IngredientInfoDTO {
    private String name;

    private String description;

    public IngredientInfoDTO() {
    }

    public String getName() {
        return name;
    }

    public IngredientInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IngredientInfoDTO setDescription(String description) {
        this.description = description;
        return this;
    }
}
