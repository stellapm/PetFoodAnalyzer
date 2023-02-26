package com.example.petfoodanalyzer.models.dtos.products;

public class RecommendedProductDTO {
    private Long id;

    private String name;

    private String picUrl;

    private String petStr;

    private int reviewsCount;

    public RecommendedProductDTO() {
    }

    public Long getId() {
        return id;
    }

    public RecommendedProductDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecommendedProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public RecommendedProductDTO setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getPetStr() {
        return petStr;
    }

    public RecommendedProductDTO setPetStr(String petStr) {
        this.petStr = petStr;
        return this;
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public RecommendedProductDTO setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
        return this;
    }
}
