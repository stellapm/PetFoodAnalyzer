package com.example.petfoodanalyzer.models.entities.ingredients;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.entities.products.Product;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ingredients")
public class Ingredient extends BaseEntity {
    @ManyToOne
    private IngredientCategory ingredientCategory;

    @Column(nullable = false, unique = true)
    private String name;

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

    public Set<Product> getProducts() {
        return products;
    }

    public Ingredient setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }

    @Override
    public String toString() {
        return String.format("------ %s - %s\n", this.name, this.description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(ingredientCategory.getName().getValue(), that.ingredientCategory.getName().getValue()) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientCategory, name, description);
    }
}
