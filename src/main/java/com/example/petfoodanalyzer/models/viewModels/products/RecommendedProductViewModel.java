package com.example.petfoodanalyzer.models.viewModels.products;

public class RecommendedProductViewModel {
    private Long id;

    private String name;

    private String picUrl;

    private String petStr;

    private int reviewsCount;

    public RecommendedProductViewModel() {
    }

    public Long getId() {
        return id;
    }

    public RecommendedProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecommendedProductViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public RecommendedProductViewModel setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getPetStr() {
        return petStr;
    }

    public RecommendedProductViewModel setPetStr(String petStr) {
        this.petStr = petStr;
        return this;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public RecommendedProductViewModel setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
        return this;
    }
}
