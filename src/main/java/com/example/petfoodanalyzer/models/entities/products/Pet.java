package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "pets_type", nullable = false)
    private PetsTypes petsType;

    public Pet() {
    }

    public Pet(PetsTypes petsType) {
        this.petsType = petsType;
    }

    public PetsTypes getPetsType() {
        return petsType;
    }

    public Pet setPetsType(PetsTypes petsName) {
        this.petsType = petsName;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return petsType.name().equals(pet.petsType.name());
    }

    @Override
    public int hashCode() {
        return Objects.hash(petsType);
    }
}
