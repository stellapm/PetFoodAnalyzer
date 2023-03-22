package com.example.petfoodanalyzer.models.entities.ingredients;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.enums.IngredientCategoryNames;
import jakarta.persistence.*;

import java.util.Objects;

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

    @Override
    public String toString() {
        return String.format("* %s - %s:\n", this.name.getValue(), this.getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientCategory that = (IngredientCategory) o;
        return Objects.equals(name.getValue(), that.name.getValue()) && Objects.equals(name.name(), that.name.name()) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
