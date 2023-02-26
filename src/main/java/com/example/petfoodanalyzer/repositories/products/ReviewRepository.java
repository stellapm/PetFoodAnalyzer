package com.example.petfoodanalyzer.repositories.products;

import com.example.petfoodanalyzer.models.entities.products.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
