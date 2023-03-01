package com.example.petfoodanalyzer.repositories.products;

import com.example.petfoodanalyzer.models.dtos.products.ProductOverviewInfoDTO;
import com.example.petfoodanalyzer.models.dtos.products.ReviewOverviewDTO;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.pet.petsType IN :petsTypes AND p.id <> :productId " +
            "ORDER BY SIZE(p.reviews) DESC ")
    List<Product> findAllProductsByPetType(List<PetsTypes> petsTypes, Long productId);

    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE p.brand.id = :id")
    List<Product> findByBrandId(Long id);

}
