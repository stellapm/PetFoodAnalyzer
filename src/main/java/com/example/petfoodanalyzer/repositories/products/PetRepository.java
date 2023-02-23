package com.example.petfoodanalyzer.repositories.products;

import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    @Query("SELECT p " +
            "FROM Pet p " +
            "WHERE p.petsType = :petsType")
    Pet findByPetsType(PetsTypes petsType);
}
