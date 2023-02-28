package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {
    @ManyToOne
    private UserEntity author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @ManyToOne
    private Product product;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserEntity> likes;

    public Review() {
        this.likes = new HashSet<>();
    }

    public UserEntity getAuthor() {
        return author;
    }

    public Review setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Review setContent(String content) {
        this.content = content;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public Review setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Review setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Set<UserEntity> getLikes() {
        return likes;
    }

    public Review setLikes(Set<UserEntity> likes) {
        this.likes = likes;
        return this;
    }
}
