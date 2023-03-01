package com.example.petfoodanalyzer.repositories.products;

import com.example.petfoodanalyzer.models.dtos.products.ReviewOverviewDTO;
import com.example.petfoodanalyzer.models.entities.products.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r " +
            "FROM Review r " +
            "where r.id IN " +
            "(SELECT DISTINCT r.product.id FROM Review r) " +
            "ORDER BY r.createdOn DESC ")
    List<Review> findMostReviewed();
}
