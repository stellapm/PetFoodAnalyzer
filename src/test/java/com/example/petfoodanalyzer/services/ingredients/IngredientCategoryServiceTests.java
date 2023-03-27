package com.example.petfoodanalyzer.services.ingredients;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.entities.ingredients.IngredientCategory;
import com.example.petfoodanalyzer.models.enums.IngredientCategoryNames;
import com.example.petfoodanalyzer.repositories.ingredients.IngredientCategoryRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IngredientCategoryServiceTests {
    private final static String INVALID_STR = "Invalid";

    private IngredientCategoryService testService;

    private IngredientCategory first;
    private IngredientCategory second;
    private IngredientCategory third;


    @Mock
    private IngredientCategoryRepository mockRepository;

    @BeforeEach
    public void setupCategories(){
        this.first = new IngredientCategory()
                .setName(IngredientCategoryNames.CL)
                .setDescription("example");

        this.second = new IngredientCategory()
                .setName(IngredientCategoryNames.AP)
                .setDescription("example");

        this.third = new IngredientCategory()
                .setName(IngredientCategoryNames.AA)
                .setDescription("example");

        this.testService = new IngredientCategoryService(this.mockRepository, new Gson());
    }

    @Test
    public void testIsIngredientCatInit(){
        when(this.mockRepository.count()).thenReturn(0L);
        assertFalse(this.testService.isIngredientCatInit(), "Repository should be empty!");

        when(this.mockRepository.count()).thenReturn(1L);
        assertTrue(this.testService.isIngredientCatInit(), "Repository should be populated!");
    }

    @Test
    public void testReadIngredientCategoriesFileContent() throws IOException {
        String json = this.testService.readIngredientCategoriesFileContent();
        assertTrue(json.contains("Essential amino"));
    }

    @Test
    public void testInitIngredientCategoriesNoActionOnInitDB() throws IOException {
        when(this.mockRepository.count()).thenReturn(1L);

        this.testService.initIngredientCategories();

        verify(this.mockRepository, times(0)).saveAll(any());
    }

    @Test
    public void testInitIngredientCategoriesOnEmptyDB() throws IOException {
        when(this.mockRepository.count()).thenReturn(0L);

        this.testService.initIngredientCategories();

        verify(this.mockRepository).saveAll(any());
    }

    @Test
    public void testGetCategoryNameByCode(){
        IngredientCategoryNames categoryName = this.testService.getCategoryNameByCode("RV");

        assertEquals("Root Vegetables", categoryName.getValue(),
                "Category name value does not match code.");

        assertNotEquals("Root Vegetables", INVALID_STR,
                "Invalid category name should fail");
    }

    @Test
    public void testGetIngredientCategoryByName(){
        when(mockRepository.findByName(IngredientCategoryNames.CL)).thenReturn(Optional.ofNullable(first));

        IngredientCategory ingredientCategory = this.testService.getIngredientCategoryByName("Cellulose");

        assertEquals(first, ingredientCategory,
                "Ingredient category does not match");
    }

    @Test
    public void testGetIngredientCategoryByNameException(){
        Throwable invalidEnum = assertThrows(ObjectNotFoundException.class, () -> this.testService.getIngredientCategoryByName(INVALID_STR));
        assertEquals("Object with name Invalid and type Ingredient category not found!", invalidEnum.getMessage(), "Incorrect error message thrown.");

        when(this.mockRepository.findByName(IngredientCategoryNames.CL)).thenReturn(Optional.empty());

        Throwable missingEnum = assertThrows(ObjectNotFoundException.class, () -> this.testService.getIngredientCategoryByName(IngredientCategoryNames.CL.getValue()));
        assertEquals(String.format("Object with name %s and type Ingredient category not found!", IngredientCategoryNames.CL.getValue()),
                missingEnum.getMessage(), "Incorrect error message thrown.");
    }

    @Test void testGetAllIngredientCategoryNames(){
        when(this.mockRepository.findAll()).thenReturn(List.of(this.first, this.second, this.third));

        List<String> result = this.testService.getAllIngredientCategoriesNames();

        assertEquals(3, result.size(), "Invalid result list size.");
        assertEquals(this.first.getName().getValue(), result.get(0));
        assertEquals(this.second.getName().getValue(), result.get(1));
        assertEquals(this.third.getName().getValue(), result.get(2));
    }
}
