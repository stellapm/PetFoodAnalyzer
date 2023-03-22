package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.repositories.products.PetRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class PetServiceTests {
    private PetService testService;
    private Pet first;
    private Pet second;

    @Mock
    private PetRepository mockRepository;

    @BeforeEach
    public void setupPets(){
        this.first = new Pet()
                .setPetsType(PetsTypes.Cat);

        this.second = new Pet()
                .setPetsType(PetsTypes.Dog);

        this.testService = new PetService(this.mockRepository);
    }

    //TODO: test dbinit?

    @Test
    void testGetAllPetTypesAsString(){
        Mockito.when(this.mockRepository.findAll()).thenReturn(List.of(this.first, this.second));

        List<String> result = this.testService.getAllPetTypesAsString();

        Assertions.assertEquals(2, result.size(), "Invalid result list size.");
        Assertions.assertNotEquals(1, result.size());
        Assertions.assertTrue(result.contains("Cat"));
        Assertions.assertTrue(result.contains("Dog"));
        Assertions.assertFalse(result.contains("Parrot"));
    }

    @Test
    void testGetPetByName(){
        Mockito.when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Cat"))).thenReturn(Optional.ofNullable(this.first));

        Pet pet = this.testService.getPetByName("Cat");

        Assertions.assertEquals(this.first, pet);
        Assertions.assertNotEquals(this.second, pet);
    }

    @Test
    void testGetPetByNameException(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.testService.getPetByName("Parrot"));

        Mockito.when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Cat"))).thenReturn(Optional.empty());

        Throwable missingEnum = Assertions.assertThrows(ObjectNotFoundException.class, () -> this.testService.getPetByName("Cat"));
        Assertions.assertEquals("Object with name Cat and type Pet not found!", missingEnum.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    void testGetAllMatchingPetTypes(){
        Mockito.when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Cat"))).thenReturn(Optional.ofNullable(this.first));
        Mockito.when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Dog"))).thenReturn(Optional.ofNullable(this.second));

        Set<Pet> result1 = this.testService.getAllMatchingPetTypes(List.of("Cat"));
        Assertions.assertEquals(1, result1.size(), "Invalid number of pets");
        Assertions.assertNotEquals(0, result1.size(), "Pet count should be 1");
        Assertions.assertTrue(result1.contains(this.first), "Pet set does not contain correct pet");
        Assertions.assertFalse(result1.contains(this.second), "Pet set should not contain Dog");


        Set<Pet> result2 = this.testService.getAllMatchingPetTypes(List.of("Dog"));
        Assertions.assertEquals(1, result2.size(), "Invalid number of pets");
        Assertions.assertNotEquals(3, result1.size(), "Pet count should be 1");
        Assertions.assertTrue(result2.contains(this.second), "Pet set does not contain correct pet");
        Assertions.assertFalse(result2.contains(this.first), "Pet set does not contain Cat");

        Set<Pet> result3 = this.testService.getAllMatchingPetTypes(List.of("Dog", "Cat"));

        Assertions.assertEquals(2, result3.size(), "Invalid number of pets");
        Assertions.assertNotEquals(1, result3.size(), "Pet count should be 2");
        Assertions.assertTrue(result3.contains(this.first), "Pet set does not contain correct pet");
        Assertions.assertTrue(result3.contains(this.second), "Pet set does not contain correct pet");
    }

    @Test
    void testGetAllMatchingPetTypesException(){
        Mockito.when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Cat"))).thenReturn(Optional.empty());

        Throwable missingEnum = Assertions.assertThrows(ObjectNotFoundException.class, () -> this.testService.getAllMatchingPetTypes(List.of("Cat")));
        Assertions.assertEquals("Object with name Cat and type Pet not found!", missingEnum.getMessage(), "Incorrect error message thrown.");

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.testService.getPetByName("Parrot"));

    }
}
