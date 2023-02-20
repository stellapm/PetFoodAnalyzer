package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.enums.IngredientCategoryNames;
import jakarta.persistence.*;

@Entity
@Table(name = "ingredients_categories")
public class IngredientCategory extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private IngredientCategoryNames name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    public IngredientCategory() {
    }

    public IngredientCategory(IngredientCategoryNames name, String description) {
        this.name = name;
        this.description = description;
    }

    public IngredientCategoryNames getName() {
        return name;
    }

    public IngredientCategory setName(IngredientCategoryNames name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IngredientCategory setDescription(String description) {
        this.description = description;
        return this;
    }
}
