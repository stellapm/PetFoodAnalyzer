package com.example.petfoodanalyzer.models.entities.products;

import com.example.petfoodanalyzer.models.entities.BaseEntity;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "pets")
public class Pet extends BaseEntity {
    @Enumerated(EnumType.STRING)
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
}
