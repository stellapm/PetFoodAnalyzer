package com.example.petfoodanalyzer.repositories.products;

import com.example.petfoodanalyzer.models.entities.products.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name);

    @Query("SELECT b.name " +
            "FROM Brand b ")
    List<String> findAllBrandNames();
}
