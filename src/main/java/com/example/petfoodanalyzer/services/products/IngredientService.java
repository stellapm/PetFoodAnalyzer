package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.AddIngredientDTO;
import com.example.petfoodanalyzer.models.entities.products.Ingredient;
import com.example.petfoodanalyzer.models.entities.products.IngredientCategory;
import com.example.petfoodanalyzer.repositories.products.IngredientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientCategoryService ingredientCategoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, IngredientCategoryService ingredientCategoryService, ModelMapper modelMapper) {
        this.ingredientRepository = ingredientRepository;
        this.ingredientCategoryService = ingredientCategoryService;
        this.modelMapper = modelMapper;
    }

    public Ingredient findByName(String name) {
        return this.ingredientRepository.findByName(name);
    }

    public void addIngredient(AddIngredientDTO addIngredientDTO) {
        IngredientCategory ingredientCategory = this.ingredientCategoryService.getIngredientCategoryByName(addIngredientDTO.getIngredientCategoryStr());

        Ingredient ingredient = this.modelMapper.map(addIngredientDTO, Ingredient.class);
        ingredient.setIngredientCategory(ingredientCategory);

        this.ingredientRepository.save(ingredient);
    }

    public boolean allIngredientsPresent(String ingredientsList) {
        List<String> ingredientsRaw = Arrays.stream(ingredientsList.split(",\\s+")).toList();

        List<Ingredient> invalidIngredients = ingredientsRaw
                .stream()
                .map(this::findByName)
                .filter(Objects::isNull)
                .toList();

                return invalidIngredients.size() == 0;
    }
}
