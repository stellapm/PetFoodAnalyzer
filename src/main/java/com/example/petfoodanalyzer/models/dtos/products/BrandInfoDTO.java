package com.example.petfoodanalyzer.models.dtos.products;

public class BrandInfoDTO {
    private String name;

    private String description;

    private String picUrl;

    public BrandInfoDTO() {
    }

    public String getName() {
        return name;
    }

    public BrandInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BrandInfoDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public BrandInfoDTO setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }
}
