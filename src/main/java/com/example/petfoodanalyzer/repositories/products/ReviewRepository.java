package com.example.petfoodanalyzer.repositories.products;

import com.example.petfoodanalyzer.models.entities.products.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r " +
            "FROM Review r " +
            "where r.id IN " +
            "(SELECT DISTINCT r.product.id FROM Review r) " +
            "AND r.id IN (SELECT DISTINCT r.author.id FROM Review r)" +
            "ORDER BY r.createdOn DESC ")
    List<Review> findMostReviewed();


    @Query("SELECT r " +
            "from Review r " +
            "WHERE r.reported = true ")
    List<Review> findAllReported();

//    @Query("SELECT r " +
//            "FROM Review r " +
//            "WHERE r.id = :id AND r.product.id = :productId ")
//    Optional<Review> findByIdAndProductId(Long id, Long productId);
}
