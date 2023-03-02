package com.example.petfoodanalyzer.models.viewModels.products;

public class BrandOverviewViewModel {
    private Long id;
    private String picUrl;

    public BrandOverviewViewModel() {
    }

    public Long getId() {
        return id;
    }

    public BrandOverviewViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public BrandOverviewViewModel setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }
}
