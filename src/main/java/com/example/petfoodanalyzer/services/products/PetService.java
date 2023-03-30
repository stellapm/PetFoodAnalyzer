package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.repositories.products.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.petfoodanalyzer.constants.Exceptions.NAME_IDENTIFIER;
import static com.example.petfoodanalyzer.constants.Models.PET;

@Service
public class PetService {
    private PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public boolean isPetTypesInit() {
        return this.petRepository.count() > 0;
    }

    public void initPetTypes() {
        if (isPetTypesInit()) {
            return;
        }

        List<Pet> pets = Arrays.stream(PetsTypes.values())
                .map(Pet::new)
                .toList();

        this.petRepository.saveAll(pets);
    }

    //Pet is a small enough object with only one variable, will use it directly

    private List<Pet> getAllPets() {
        return this.petRepository.findAll();
    }

    public List<String> getAllPetTypesAsString() {
        return getAllPets()
                .stream()
                .map(p -> p.getPetsType().name())
                .collect(Collectors.toList());
    }

    public Set<Pet> getAllMatchingPetTypes(List<String> types) {
        return types.stream()
                .map(this::getPetByName)
                .collect(Collectors.toSet());
    }

    public Pet getPetByName(String petStr) {
        return this.petRepository.findByPetsType(PetsTypes.valueOf(petStr))
                .orElseThrow(() -> new ObjectNotFoundException(NAME_IDENTIFIER, petStr, PET));
    }

    public List<PetsTypes> getUsersPetsTypes(UserEntity userEntity) {
        List<PetsTypes> pets = new ArrayList<>();

        if (userEntity == null || userEntity.getPets().size() == 0) {
            pets = Arrays.stream(PetsTypes.values()).toList();
        } else {
            userEntity.getPets().stream()
                    .map(Pet::getPetsType)
                    .forEach(pets::add);
        }
        return pets;
    }
}
