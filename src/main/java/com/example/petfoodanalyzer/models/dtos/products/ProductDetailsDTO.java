package com.example.petfoodanalyzer.models.dtos.products;

import java.util.Set;

public class ProductDetailsDTO {
    private Long id;

    private String name;

    private String description;

    private String picUrl;

    private String brandStr;

    private String petStr;

    private String ingredientsListed;

    private boolean loggedUserFave;

    private Set<ReviewInfoDTO> reviewsInfo;

    public ProductDetailsDTO() {
    }

    public Long getId() {
        return id;
    }

    public ProductDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductDetailsDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductDetailsDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public ProductDetailsDTO setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getBrandStr() {
        return brandStr;
    }

    public ProductDetailsDTO setBrandStr(String brandStr) {
        this.brandStr = brandStr;
        return this;
    }

    public String getPetStr() {
        return petStr;
    }

    public ProductDetailsDTO setPetStr(String petStr) {
        this.petStr = petStr;
        return this;
    }

    public String getIngredientsListed() {
        return ingredientsListed;
    }

    public ProductDetailsDTO setIngredientsListed(String ingredientsListed) {
        this.ingredientsListed = ingredientsListed;
        return this;
    }

    public boolean isLoggedUserFave() {
        return loggedUserFave;
    }

    public ProductDetailsDTO setLoggedUserFave(boolean loggedUserFave) {
        this.loggedUserFave = loggedUserFave;
        return this;
    }

    public Set<ReviewInfoDTO> getReviewsInfo() {
        return reviewsInfo;
    }

    public ProductDetailsDTO setReviewsInfo(Set<ReviewInfoDTO> reviewsInfo) {
        this.reviewsInfo = reviewsInfo;
        return this;
    }
}
