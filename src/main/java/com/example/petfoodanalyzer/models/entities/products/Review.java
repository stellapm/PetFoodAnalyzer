package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.entities.users.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "reviews")
public class Review extends BaseEntity {
    @ManyToOne
    private User author;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; //TODO: at least 5 characters

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;

    @ManyToOne
    private Product product;

    @OneToMany
    private Set<User> likes;

    public Review() {
    }

    public User getAuthor() {
        return author;
    }

    public Review setAuthor(User author) {
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

    public Set<User> getLikes() {
        return likes;
    }

    public Review setLikes(Set<User> likes) {
        this.likes = likes;
        return this;
    }
}
