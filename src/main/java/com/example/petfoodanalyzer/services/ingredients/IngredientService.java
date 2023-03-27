package com.example.petfoodanalyzer.services.ingredients;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.ingredients.AddIngredientDTO;
import com.example.petfoodanalyzer.models.viewModels.ingredients.IngredientViewModel;
import com.example.petfoodanalyzer.models.dtos.ingredients.IngredientsListDTO;
import com.example.petfoodanalyzer.models.entities.ingredients.Ingredient;
import com.example.petfoodanalyzer.models.entities.ingredients.IngredientCategory;
import com.example.petfoodanalyzer.repositories.ingredients.IngredientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.example.petfoodanalyzer.constants.Models.INGREDIENT;
import static com.example.petfoodanalyzer.constants.Exceptions.NAME_IDENTIFIER;

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
        return this.ingredientRepository
                .findByName(name)
                .orElseThrow(() -> new ObjectNotFoundException(NAME_IDENTIFIER, name, INGREDIENT));
    }

    public void addIngredient(AddIngredientDTO addIngredientDTO) {
        IngredientCategory ingredientCategory = this.ingredientCategoryService.getIngredientCategoryByName(addIngredientDTO.getIngredientCategoryStr());

        Ingredient ingredient = this.modelMapper.map(addIngredientDTO, Ingredient.class);
        ingredient.setIngredientCategory(ingredientCategory);

        this.ingredientRepository.save(ingredient);
    }

    public boolean allIngredientsPresent(String ingredientsList) {
        List<String> ingredientsRaw = getIngredientNamesFromString(ingredientsList);

        try {
            ingredientsRaw
                    .forEach(this::findByName);

            return true;
        } catch (ObjectNotFoundException e){
            return false;
        }
    }

    public List<String> getIngredientNamesFromString(String ingredientsList){
        return Arrays.stream(ingredientsList.split(",\\s+")).toList();
    }

    public Map<String, List<String>> analyzeIngredients(IngredientsListDTO ingredientsListDTO) {
        List<String> rawIngredients = getIngredientNamesFromString(ingredientsListDTO.getIngredientsList());
        Map<String, List<String >> analyzeResult = new LinkedHashMap<>();

        List<Ingredient> allIngredients = getIngredientsFromStringNames(rawIngredients);

        for (Ingredient ingredient : allIngredients) {
            String categoryInfo = ingredient.getIngredientCategory().toString();

            if (!analyzeResult.containsKey(categoryInfo)){
                analyzeResult.put(categoryInfo, new ArrayList<String>());
            }

            analyzeResult.get(categoryInfo).add(ingredient.toString());
        }

        return analyzeResult;
    }

    public List<Ingredient> getIngredientsFromStringNames(List<String> rawIngredients) {
        return rawIngredients.stream()
                .map(this::findByName)
                .sorted(Comparator.comparing(i -> i.getIngredientCategory().getName().getValue()))
                .toList();
    }

    public List<IngredientViewModel> getAllIngredients() {
        return this.ingredientRepository.findAll()
                .stream()
                .map(i -> this.modelMapper.map(i, IngredientViewModel.class))
                .collect(Collectors.toList());
    }

    public String stringifyIngredientNames(Set<Ingredient> ingredients) {
        List<String> ingredientsNames = ingredients.stream()
                .map(Ingredient::getName)
                .toList();

        return String.join(", ", ingredientsNames);
    }
}
