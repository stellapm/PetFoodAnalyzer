package com.example.petfoodanalyzer.models.dtos.products;

import java.time.LocalDateTime;

public class ReviewInfoDTO {
    private Long id;

    private String authorUsername;

    private String authorProfilePic;

    private String content;

    private LocalDateTime createdOn;

    private int likesCount;

    public ReviewInfoDTO() {
    }

    public Long getId() {
        return id;
    }

    public ReviewInfoDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public ReviewInfoDTO setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
        return this;
    }

    public String getAuthorProfilePic() {
        return authorProfilePic;
    }

    public ReviewInfoDTO setAuthorProfilePic(String authorProfilePic) {
        this.authorProfilePic = authorProfilePic;
        return this;
    }

    public String getContent() {
        return content;
    }

    public ReviewInfoDTO setContent(String content) {
        this.content = content;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public ReviewInfoDTO setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public ReviewInfoDTO setLikesCount(int likesCount) {
        this.likesCount = likesCount;
        return this;
    }
}