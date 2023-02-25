package com.example.petfoodanalyzer.models.dtos.products;

public class ProductOverviewInfoDTO {
    private Long id;

    private String name;

    private String description;

    private String picUrl;

    private String brandStr;

    private String petStr;

    public ProductOverviewInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public ProductOverviewInfoDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductOverviewInfoDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductOverviewInfoDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public ProductOverviewInfoDTO setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getBrandStr() {
        return brandStr;
    }

    public ProductOverviewInfoDTO setBrandStr(String brandStr) {
        this.brandStr = brandStr;
        return this;
    }

    public String getPetStr() {
        return petStr;
    }

    public ProductOverviewInfoDTO setPetStr(String petStr) {
        this.petStr = petStr;
        return this;
    }
}
