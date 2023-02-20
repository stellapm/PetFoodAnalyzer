package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {
    @ManyToOne
    private IngredientCategory ingredientCategory;

    @Column(nullable = false, unique = true)
    private String name; // TODO: from 3 to 20 characters

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Product> products;

    public Ingredient() {
    }

    public IngredientCategory getIngredientCategory() {
        return ingredientCategory;
    }

    public Ingredient setIngredientCategory(IngredientCategory ingredientCategory) {
        this.ingredientCategory = ingredientCategory;
        return this;
    }

    public String getName() {
        return name;
    }

    public Ingredient setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Ingredient setDescription(String description) {
        this.description = description;
        return this;
    }
}
