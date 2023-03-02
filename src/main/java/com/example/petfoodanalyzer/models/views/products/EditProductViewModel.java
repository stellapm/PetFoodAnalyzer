package com.example.petfoodanalyzer.models.views.products;

public class EditProductViewModel {
    private Long id;

    private String name;

    private String description;

    private String picUrl;

    private String brandStr;

    private String petStr;

    private String ingredientsList;

    public Long getId() {
        return id;
    }

    public EditProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EditProductViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public EditProductViewModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public EditProductViewModel setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }

    public String getBrandStr() {
        return brandStr;
    }

    public EditProductViewModel setBrandStr(String brandStr) {
        this.brandStr = brandStr;
        return this;
    }

    public String getPetStr() {
        return petStr;
    }

    public EditProductViewModel setPetStr(String petStr) {
        this.petStr = petStr;
        return this;
    }

    public String getIngredientsList() {
        return ingredientsList;
    }

    public EditProductViewModel setIngredientsList(String ingredientsList) {
        this.ingredientsList = ingredientsList;
        return this;
    }
}
