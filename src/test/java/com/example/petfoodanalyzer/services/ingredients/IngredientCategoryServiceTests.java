package com.example.petfoodanalyzer.services.ingredients;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.entities.ingredients.IngredientCategory;
import com.example.petfoodanalyzer.models.enums.IngredientCategoryNames;
import com.example.petfoodanalyzer.repositories.ingredients.IngredientCategoryRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IngredientCategoryServiceTests {
    private final static String INVALID_STR = "Invalid";

    private IngredientCategoryService testService;

    private IngredientCategory first;
    private IngredientCategory second;
    private IngredientCategory third;


    @Mock
    IngredientCategoryRepository mockRepository;

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

    //TODO: Test db init + json file read/import?

    @Test
    public void testGetCategoryNameByCode(){
        IngredientCategoryNames categoryName = IngredientCategoryNames.valueOf("RV");

        assertEquals("Root Vegetables", categoryName.getValue(),
                "Category name value does not match code.");

        assertNotEquals("Root Vegetables", INVALID_STR,
                "Invalid category name should fail");
    }

    @Test
    public void testGetIngredientCategoryByName(){
        Mockito.when(mockRepository.findByName(IngredientCategoryNames.CL)).thenReturn(Optional.ofNullable(first));

        IngredientCategory ingredientCategory = this.testService.getIngredientCategoryByName("Cellulose");

        assertEquals(first, ingredientCategory,
                "Ingredient category does not match");
    }

    @Test
    public void testGetIngredientCategoryByNameException(){
        Throwable invalidEnum = assertThrows(ObjectNotFoundException.class, () -> this.testService.getIngredientCategoryByName(INVALID_STR));
        assertEquals("Object with name Invalid and type Ingredient category not found!", invalidEnum.getMessage(), "Incorrect error message thrown.");

        Mockito.when(this.mockRepository.findByName(IngredientCategoryNames.CL)).thenReturn(Optional.empty());

        Throwable missingEnum = assertThrows(ObjectNotFoundException.class, () -> this.testService.getIngredientCategoryByName(IngredientCategoryNames.CL.getValue()));
        assertEquals(String.format("Object with name %s and type Ingredient category not found!", IngredientCategoryNames.CL.getValue()),
                missingEnum.getMessage(), "Incorrect error message thrown.");
    }

    @Test void testGetAllIngredientCategoryNames(){
        Mockito.when(this.mockRepository.findAll()).thenReturn(List.of(this.first, this.second, this.third));

        List<String> result = this.testService.getAllIngredientCategoriesNames();

        assertEquals(3, result.size(), "Invalid result list size.");
        assertEquals(this.first.getName().getValue(), result.get(0));
        assertEquals(this.second.getName().getValue(), result.get(1));
        assertEquals(this.third.getName().getValue(), result.get(2));
    }
}
