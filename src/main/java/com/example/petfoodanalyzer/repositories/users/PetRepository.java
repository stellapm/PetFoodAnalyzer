package com.example.petfoodanalyzer.repositories.users;

import com.example.petfoodanalyzer.models.entities.users.Pet;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {
    @Query("SELECT p " +
            "FROM Pet p " +
            "WHERE p.petsType = :petsType")
    Pet findByPetsType(PetsTypes petsType);
}
