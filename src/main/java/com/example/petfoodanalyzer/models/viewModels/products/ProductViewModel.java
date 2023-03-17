package com.example.petfoodanalyzer.models.viewModels.products;

import java.util.Set;

public class ProductViewModel {
    private Long id;

    private String name;

    private String description;

    private String picUrl;

    private String brandStr;

    private String petStr;

    private String ingredientsListed;

    private boolean loggedUserFave;

    private Set<ReviewInfoViewModel> reviewsInfo;

    public ProductViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public ProductViewModel setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getBrandStr() {
        return brandStr;
    }

    public ProductViewModel setBrandStr(String brandStr) {
        this.brandStr = brandStr;
        return this;
    }

    public String getPetStr() {
        return petStr;
    }

    public ProductViewModel setPetStr(String petStr) {
        this.petStr = petStr;
        return this;
    }

    public String getIngredientsListed() {
        return ingredientsListed;
    }

    public ProductViewModel setIngredientsListed(String ingredientsListed) {
        this.ingredientsListed = ingredientsListed;
        return this;
    }

    public boolean isLoggedUserFave() {
        return loggedUserFave;
    }

    public ProductViewModel setLoggedUserFave(boolean loggedUserFave) {
        this.loggedUserFave = loggedUserFave;
        return this;
    }

    public Set<ReviewInfoViewModel> getReviewsInfo() {
        return reviewsInfo;
    }

    public ProductViewModel setReviewsInfo(Set<ReviewInfoViewModel> reviewsInfo) {
        this.reviewsInfo = reviewsInfo;
        return this;
    }
}
