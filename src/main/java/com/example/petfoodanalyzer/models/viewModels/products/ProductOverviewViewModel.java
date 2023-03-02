package com.example.petfoodanalyzer.models.viewModels.products;

public class ProductOverviewViewModel {
    private Long id;

    private String name;

    private String description;

    private String picUrl;

    private String brandStr;

    private String petStr;

    public ProductOverviewViewModel() {
    }

    public Long getId() {
        return id;
    }

    public ProductOverviewViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductOverviewViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProductOverviewViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public ProductOverviewViewModel setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getBrandStr() {
        return brandStr;
    }

    public ProductOverviewViewModel setBrandStr(String brandStr) {
        this.brandStr = brandStr;
        return this;
    }

    public String getPetStr() {
        return petStr;
    }

    public ProductOverviewViewModel setPetStr(String petStr) {
        this.petStr = petStr;
        return this;
    }
}
