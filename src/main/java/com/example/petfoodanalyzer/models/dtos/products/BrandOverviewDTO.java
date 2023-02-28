package com.example.petfoodanalyzer.models.dtos.products;

public class BrandOverviewDTO {
    private Long id;
    private String picUrl;

    public BrandOverviewDTO() {
    }

    public Long getId() {
        return id;
    }

    public BrandOverviewDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public BrandOverviewDTO setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }
}
