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

@ExtendWith(MockitoExtension.class)
public class IngredientCategoryServiceTest {
    private final static String INVALID_STR = "Invalid";

    private IngredientCategory first;
    private IngredientCategory second;
    private IngredientCategory third;

    private IngredientCategoryService testService;

    @Mock
    IngredientCategoryRepository mockRepository;

    @BeforeEach
    public void setupCategories(){
        first = new IngredientCategory()
                .setName(IngredientCategoryNames.CL)
                .setDescription("example");

        second = new IngredientCategory()
                .setName(IngredientCategoryNames.AP)
                .setDescription("example");

        third = new IngredientCategory()
                .setName(IngredientCategoryNames.AA)
                .setDescription("example");

        testService = new IngredientCategoryService(mockRepository, new Gson());
    }

    @Test
    public void testGetCategoryNameByCode(){
        IngredientCategoryNames categoryName = IngredientCategoryNames.valueOf("RV");

        Assertions.assertEquals("Root Vegetables", categoryName.getValue(),
                "Category name value does not match code.");

        Assertions.assertNotEquals("Root Vegetables", INVALID_STR,
                "Invalid category name should fail");
    }

    @Test
    public void testGetIngredientCategoryByName(){
        Mockito.when(mockRepository.findByName(IngredientCategoryNames.CL)).thenReturn(Optional.ofNullable(first));

        IngredientCategory ingredientCategory = this.testService.getIngredientCategoryByName("Cellulose");

        Assertions.assertEquals(mockRepository.findByName(IngredientCategoryNames.CL).get().getName(), ingredientCategory.getName(),
                "Ingredient category name does not match");
        Assertions.assertEquals(mockRepository.findByName(IngredientCategoryNames.CL).get().getName().getValue(), ingredientCategory.getName().getValue(),
                "Ingredient category value does not match");
    }

    @Test
    public void testGetIngredientCategoryByNameException(){

        Throwable error = Assertions.assertThrows(ObjectNotFoundException.class, () -> this.testService.getIngredientCategoryByName(INVALID_STR));
        Assertions.assertEquals("Object with name Invalid and type Ingredient category not found!", error.getMessage(), "Incorrect error message thrown.");

        Mockito.when(mockRepository.findByName(IngredientCategoryNames.CL)).thenReturn(Optional.empty());

        error = Assertions.assertThrows(ObjectNotFoundException.class, () -> this.testService.getIngredientCategoryByName(IngredientCategoryNames.CL.getValue()));
        Assertions.assertEquals(String.format("Object with name %s and type Ingredient category not found!", IngredientCategoryNames.CL.getValue()),
                error.getMessage(), "Incorrect error message thrown.");
    }

    @Test void testGetAllIngredientCategoryNames(){
        Mockito.when(mockRepository.findAll()).thenReturn(List.of(first, second, third));

        List<String> result = this.testService.getAllIngredientCategoriesNames();

        Assertions.assertEquals(mockRepository.findAll().size(), result.size(), "Invalid result list size.");
        Assertions.assertEquals(first.getName().getValue(), result.get(0));
        Assertions.assertEquals(second.getName().getValue(), result.get(1));
        Assertions.assertEquals(third.getName().getValue(), result.get(2));
    }
}
