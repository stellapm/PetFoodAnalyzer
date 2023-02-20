package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.entities.users.Pet;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name; // TODO: from 3 to 20 characters

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description; //TODO: from 5 to 50 characters

    @Column(name = "pic_url", nullable = false)
    private String picUrl;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Pet pet;

    @ManyToMany
    @JoinTable(
            name = "product_ingredients",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients;

    @OneToMany(targetEntity = Review.class, mappedBy = "product")
    private Set<Review> reviews;

    public Product() {
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public Product setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public Brand getBrand() {
        return brand;
    }

    public Product setBrand(Brand brand) {
        this.brand = brand;
        return this;
    }

    public Pet getPet() {
        return pet;
    }

    public Product setPet(Pet pet) {
        this.pet = pet;
        return this;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Product setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Product setReviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }
}
