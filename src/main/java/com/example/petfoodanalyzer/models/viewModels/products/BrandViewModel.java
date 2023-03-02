package com.example.petfoodanalyzer.models.viewModels.products;

public class BrandViewModel {
    private String name;

    private String description;

    private String picUrl;

    public BrandViewModel() {
    }

    public String getName() {
        return name;
    }

    public BrandViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BrandViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public BrandViewModel setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }
}
