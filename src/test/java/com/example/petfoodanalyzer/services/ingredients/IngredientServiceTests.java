package com.example.petfoodanalyzer.services.ingredients;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.ingredients.AddIngredientDTO;
import com.example.petfoodanalyzer.models.dtos.ingredients.IngredientsListDTO;
import com.example.petfoodanalyzer.models.entities.ingredients.Ingredient;
import com.example.petfoodanalyzer.models.entities.ingredients.IngredientCategory;
import com.example.petfoodanalyzer.models.enums.IngredientCategoryNames;
import com.example.petfoodanalyzer.models.viewModels.ingredients.IngredientViewModel;
import com.example.petfoodanalyzer.repositories.ingredients.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IngredientServiceTests {
    private Ingredient first;
    private IngredientCategory firstIC;
    private Ingredient second;
    private IngredientCategory secondIC;

    @Mock
    private IngredientRepository mockRepository;

    @Mock
    private IngredientCategoryService ingredientCategoryService;
    private IngredientService testService;

    @BeforeEach
    public void setup(){
        setupTestIngredients();

        this.testService = new IngredientService(this.mockRepository, this.ingredientCategoryService, new ModelMapper());
    }

    private void setupTestIngredients() {
        this.firstIC = new IngredientCategory()
                .setName(IngredientCategoryNames.CL)
                .setDescription("example");

        this.secondIC = new IngredientCategory()
                .setName(IngredientCategoryNames.AP)
                .setDescription("example");

        this.first = new Ingredient()
                .setName("First")
                .setDescription("Example")
                .setIngredientCategory(firstIC);

        this.second = new Ingredient()
                .setName("Second")
                .setDescription("Example2")
                .setIngredientCategory(secondIC);
    }

    @Test
    public void testFindByName(){
        when(this.mockRepository.findByName("First"))
                .thenReturn(Optional.ofNullable(this.first));

        Ingredient result = this.testService.findByName("First");

        assertEquals(this.first, result);
        assertNotEquals(this.second, result);
    }

    @Test
    public void testFindByNameException(){
        when(this.mockRepository.findByName("First"))
                .thenReturn(Optional.empty());

        Throwable missingName = assertThrows(ObjectNotFoundException.class, () -> this.testService.findByName("First"));
        assertEquals("Object with name First and type Ingredient not found!", missingName.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    public void testAddIngredient(){
        when(ingredientCategoryService.getIngredientCategoryByName("Cellulose")).thenReturn(firstIC);

        AddIngredientDTO dto = new AddIngredientDTO()
                .setIngredientCategoryStr("Cellulose")
                .setName("First")
                .setDescription("Example");

        this.testService.addIngredient(dto);

        verify(mockRepository).save(any());
    }

    @Test
    public void testAllIngredientsPresent(){
        when(mockRepository.findByName("First")).thenReturn(Optional.ofNullable(this.first));
        when(mockRepository.findByName("Second")).thenReturn(Optional.ofNullable(this.second));
        when(mockRepository.findByName("Third")).thenReturn(Optional.empty());

        assertTrue(this.testService.allIngredientsPresent("First, Second"));
        assertFalse(this.testService.allIngredientsPresent("First, Second, Third"));
    }

    @Test
    public void testGetIngredientNamesFromString(){
        List<String> result = this.testService.getIngredientNamesFromString("First, Second");

        assertEquals(2, result.size());
        assertTrue(result.contains("First"));
        assertTrue(result.contains("Second"));
    }

    @Test
    public void testAnalyzeIngredientsReturnsMapWithCorrectKeys(){
        when(mockRepository.findByName("First")).thenReturn(Optional.ofNullable(this.first));
        when(mockRepository.findByName("Second")).thenReturn(Optional.ofNullable(this.second));


        IngredientsListDTO dto = new IngredientsListDTO()
                .setIngredientsList("First, Second");

        Map<String, List<String>> result = this.testService.analyzeIngredients(dto);

        assertEquals(2, result.size());

        String key1 = (String) result.keySet().toArray()[0];
        String key2 = (String) result.keySet().toArray()[1];

        assertTrue(key1.contains("Animal Products"));
        assertTrue(key2.contains("Cellulose"));
    }

    @Test
    public void testAnalyzeIngredientsReturnsMapWithCorrectValues(){
        when(mockRepository.findByName("First")).thenReturn(Optional.ofNullable(this.first));
        when(mockRepository.findByName("Second")).thenReturn(Optional.ofNullable(this.second));


        IngredientsListDTO dto = new IngredientsListDTO()
                .setIngredientsList("First, Second");

        Map<String, List<String>> result = this.testService.analyzeIngredients(dto);

        List<String> value1 = (List<String>) result.values().toArray()[0];
        assertEquals(1, value1.size());
        assertTrue(value1.get(0).contains("Second"));

        List<String> value2 = (List<String>) result.values().toArray()[1];
        assertEquals(1, value2.size());
        assertTrue(value2.get(0).contains("First"));
    }

    @Test
    public void testGetIngredientsFromStringNames(){
        when(mockRepository.findByName("First")).thenReturn(Optional.ofNullable(this.first));
        when(mockRepository.findByName("Second")).thenReturn(Optional.ofNullable(this.second));

        List<Ingredient> result = this.testService.getIngredientsFromStringNames(List.of("First", "Second"));

        assertEquals(2, result.size());
        assertTrue(result.contains(this.first));
        assertTrue(result.contains(this.second));
    }

    @Test
    public void testGetIngredientsInCorrectOrder(){
        when(mockRepository.findByName("First")).thenReturn(Optional.ofNullable(this.first));
        when(mockRepository.findByName("Second")).thenReturn(Optional.ofNullable(this.second));

        List<Ingredient> result = this.testService.getIngredientsFromStringNames(List.of("First", "Second"));

        assertEquals(result.get(0), this.second);
        assertEquals(result.get(1), this.first);
    }

    @Test
    public void testGetAllIngredients(){
        when(this.mockRepository.findAll()).thenReturn(List.of(this.first, this.second));

        List<IngredientViewModel> result = this.testService.getAllIngredients();

        assertEquals(2, result.size());

        assertEquals(this.first.getName(), result.get(0).getName());
        assertEquals(this.first.getDescription(), result.get(0).getDescription());

        assertEquals(this.second.getName(), result.get(1).getName());
        assertEquals(this.second.getDescription(), result.get(1).getDescription());
    }

    @Test
    public void testStringifyIngredientNames(){
        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(this.first);
        ingredients.add(this.second);

        String result = this.testService.stringifyIngredientNames(ingredients);

        assertTrue(result.contains(this.first.getName()));
        assertTrue(result.contains(this.second.getName()));
    }
}
