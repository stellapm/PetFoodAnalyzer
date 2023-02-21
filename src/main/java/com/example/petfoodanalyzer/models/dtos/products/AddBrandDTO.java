package com.example.petfoodanalyzer.models.dtos.products;

import com.example.petfoodanalyzer.validators.annotations.UniqueBrandName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AddBrandDTO {
    @Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters")
    @UniqueBrandName
    @NotBlank(message = "Please enter name.")
    private String name;

    @NotBlank(message = "Please enter description.")
    @Size(min = 5, message = "Description should at least 5 characters.")
    private String description;

    @NotBlank(message = "Please enter a valid URL.")
    private String picUrl;

    public AddBrandDTO() {
    }

    public String getName() {
        return name;
    }

    public AddBrandDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public AddBrandDTO setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public AddBrandDTO setPicUrl(String picUrl) {
        this.picUrl = picUrl;
        return this;
    }
}
