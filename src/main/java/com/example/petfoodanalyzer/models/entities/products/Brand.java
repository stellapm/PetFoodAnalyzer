package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "brands")
public class Brand extends BaseEntity {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, name = "pic_url")
    private String picUrl;

    @OneToMany(targetEntity = Product.class, mappedBy = "brand")
    private Set<Product> products;

    public Brand() {
        this.products = new HashSet<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brand brand = (Brand) o;
        return Objects.equals(name, brand.name) &&
                Objects.equals(description, brand.description) &&
                Objects.equals(picUrl, brand.picUrl) &&
                Objects.equals(products.size(), brand.products.size());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, picUrl, products);
    }
}
