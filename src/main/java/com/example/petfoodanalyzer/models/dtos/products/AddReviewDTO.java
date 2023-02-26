package com.example.petfoodanalyzer.models.dtos.products;

import jakarta.validation.constraints.Size;

public class AddReviewDTO {
    @Size(min = 5, message = "Review should have at least 5 characters.")
    private String content;

    public AddReviewDTO() {
    }

    public String getContent() {
        return content;
    }

    public AddReviewDTO setContent(String content) {
        this.content = content;
        return this;
    }
}
