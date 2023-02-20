package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name; // TODO: from 3 to 20 characters

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description; //TODO: from 5 to 50 characters

    @Column(nullable = false, name = "pic_url")
    private String picUrl;

    @OneToMany(targetEntity = Product.class, mappedBy = "brand")
    private Set<Product> products;

    public Brand() {
    }

    public String getName() {
        return name;
    }

    public Brand setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Brand setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public Brand setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Brand setProducts(Set<Product> products) {
        this.products = products;
        return this;
    }
}
