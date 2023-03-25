package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.repositories.products.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

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

    @Test
    public void testInitPetsNoActionOnInitDB() {
        when(this.mockRepository.count()).thenReturn(1L);

        this.testService.initPetTypes();

        verify(this.mockRepository, times(0)).saveAll(any());
    }

    @Test
    public void testInitPetsOnEmptyDB() {
        when(this.mockRepository.count()).thenReturn(0L);

        this.testService.initPetTypes();

        verify(this.mockRepository).saveAll(any());
    }

    @Test
    public void testIsPetTypesInit(){
        when(this.mockRepository.count()).thenReturn(0L);
        assertFalse(this.testService.isPetTypesInit(), "Repository should be empty!");

        when(this.mockRepository.count()).thenReturn(1L);
        assertTrue(this.testService.isPetTypesInit(), "Repository should be populated!");
    }

    @Test
    void testGetAllPetTypesAsString(){
        when(this.mockRepository.findAll())
                .thenReturn(List.of(this.first, this.second));

        List<String> result = this.testService.getAllPetTypesAsString();

        assertEquals(2, result.size(), "Invalid result list size.");
        assertNotEquals(1, result.size());
        assertTrue(result.contains("Cat"));
        assertTrue(result.contains("Dog"));
        assertFalse(result.contains("Parrot"));
    }

    @Test
    void testGetPetByName(){
        when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Cat")))
                .thenReturn(Optional.ofNullable(this.first));

        Pet pet = this.testService.getPetByName("Cat");

        assertEquals(this.first, pet);
        assertNotEquals(this.second, pet);
    }

    @Test
    void testGetPetByNameException(){
        assertThrows(IllegalArgumentException.class, () -> this.testService.getPetByName("Parrot"));

        when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Cat")))
                .thenReturn(Optional.empty());

        Throwable missingEnum = assertThrows(ObjectNotFoundException.class, () -> this.testService.getPetByName("Cat"));
        assertEquals("Object with name Cat and type Pet not found!", missingEnum.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    void testGetAllMatchingPetTypes(){
        when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Cat")))
                .thenReturn(Optional.ofNullable(this.first));
        when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Dog")))
                .thenReturn(Optional.ofNullable(this.second));

        Set<Pet> result1 = this.testService.getAllMatchingPetTypes(List.of("Cat"));
        assertEquals(1, result1.size(), "Invalid number of pets");
        assertNotEquals(0, result1.size(), "Pet count should be 1");
        assertTrue(result1.contains(this.first), "Pet set does not contain correct pet");
        assertFalse(result1.contains(this.second), "Pet set should not contain Dog");


        Set<Pet> result2 = this.testService.getAllMatchingPetTypes(List.of("Dog"));
        assertEquals(1, result2.size(), "Invalid number of pets");
        assertNotEquals(3, result1.size(), "Pet count should be 1");
        assertTrue(result2.contains(this.second), "Pet set does not contain correct pet");
        assertFalse(result2.contains(this.first), "Pet set does not contain Cat");

        Set<Pet> result3 = this.testService.getAllMatchingPetTypes(List.of("Dog", "Cat"));

        assertEquals(2, result3.size(), "Invalid number of pets");
        assertNotEquals(1, result3.size(), "Pet count should be 2");
        assertTrue(result3.contains(this.first), "Pet set does not contain correct pet");
        assertTrue(result3.contains(this.second), "Pet set does not contain correct pet");
    }

    @Test
    void testGetAllMatchingPetTypesException(){
        when(this.mockRepository.findByPetsType(PetsTypes.valueOf("Cat")))
                .thenReturn(Optional.empty());

        Throwable missingEnum = assertThrows(ObjectNotFoundException.class, () -> this.testService.getAllMatchingPetTypes(List.of("Cat")));
        assertEquals("Object with name Cat and type Pet not found!", missingEnum.getMessage(), "Incorrect error message thrown.");

        assertThrows(IllegalArgumentException.class, () -> this.testService.getPetByName("Parrot"));

    }
}
