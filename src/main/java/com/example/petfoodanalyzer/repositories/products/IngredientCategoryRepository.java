package com.example.petfoodanalyzer.repositories.products;

import com.example.petfoodanalyzer.models.entities.products.IngredientCategory;
import com.example.petfoodanalyzer.models.enums.IngredientCategoryNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
    IngredientCategory findByName(IngredientCategoryNames valueOf);
}
