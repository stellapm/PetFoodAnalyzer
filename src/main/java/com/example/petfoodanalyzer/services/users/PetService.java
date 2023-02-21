package com.example.petfoodanalyzer.services.users;

import com.example.petfoodanalyzer.models.entities.users.Pet;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.repositories.users.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PetService {
    private PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    private boolean isPetTypesInit(){
        return this.petRepository.count() > 0;
    }

    public void initPetTypes() {
        if (isPetTypesInit()){
            return;
        }

        List<Pet> pets = Arrays.stream(PetsTypes.values())
                .map(Pet::new)
                .toList();

        this.petRepository.saveAll(pets);
    }

    //Pet is a small enough object with only one variable. No need to pass to DTO

    private List<Pet> getAllPets(){
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
                .map(PetsTypes::valueOf)
                .map(p -> this.petRepository.findByPetsType(p))
                .collect(Collectors.toSet());
    }

    public Pet getPetByName(String petStr) {
        return this.petRepository.findByPetsType(PetsTypes.valueOf(petStr));
    }
}
