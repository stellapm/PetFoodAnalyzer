package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
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

    @Basic
    private boolean reported;

    @ManyToOne
    private Product product;

    public Review() {

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

    public boolean isReported() {
        return reported;
    }

    public Review setReported(boolean reported) {
        this.reported = reported;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Review setProduct(Product product) {
        this.product = product;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return reported == review.reported &&
                Objects.equals(author.getEmail(), review.author.getEmail()) &&
                Objects.equals(content, review.content) &&
                Objects.equals(product.getName(), review.product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, content, reported, product);
    }
}
