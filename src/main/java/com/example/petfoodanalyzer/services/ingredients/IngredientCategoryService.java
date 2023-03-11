package com.example.petfoodanalyzer.services.ingredients;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.ingredients.IngredientCategoryInitDTO;
import com.example.petfoodanalyzer.models.entities.ingredients.IngredientCategory;
import com.example.petfoodanalyzer.models.enums.IngredientCategoryNames;
import com.example.petfoodanalyzer.repositories.ingredients.IngredientCategoryRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.petfoodanalyzer.constants.Exceptions.NAME_IDENTIFIER;
import static com.example.petfoodanalyzer.constants.Models.INGREDIENT;
import static com.example.petfoodanalyzer.constants.Models.INGREDIENT_CATEGORY;

@Service
public class IngredientCategoryService {
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final Gson gson;

    @Autowired
    public IngredientCategoryService(IngredientCategoryRepository ingredientCategoryRepository, Gson gson) {
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.gson = gson;
    }

    public String readIngredientCategoriesFileContent() throws IOException {
        return Files.readString(Path.of("src/main/resources/files/json/ingredientCategoriesInfo.json"));
    }

    public boolean isIngredientCatInit(){
        return ingredientCategoryRepository.count() > 0;
    }

    public void initIngredientCategories() throws IOException {
        if (isIngredientCatInit()){
            return;
        }

        IngredientCategoryInitDTO[] ingredientsCategoriesDTO = this.gson.fromJson(
                readIngredientCategoriesFileContent(),
                IngredientCategoryInitDTO[].class);

        List<IngredientCategory> categoryNames = Arrays.stream(ingredientsCategoriesDTO)
                .map(cn -> new IngredientCategory(getCategoryNameByCode(cn.getCode()), cn.getDescription()))
                .toList();

        this.ingredientCategoryRepository.saveAll(categoryNames);
    }

    private IngredientCategoryNames getCategoryNameByCode(String code){
        return IngredientCategoryNames.valueOf(code);
    }

    public List<String> getAllIngredientCategoriesNames() {
        return this.ingredientCategoryRepository.findAll()
                .stream()
                .map(ic -> ic.getName().getValue())
                .collect(Collectors.toList());
    }

    public IngredientCategory getIngredientCategoryByName(String ingredientCategory) {
        IngredientCategoryNames ingredientCategoryName = Arrays.stream(IngredientCategoryNames.values())
                .filter(cn -> cn.getValue().equals(ingredientCategory))
                .findFirst().get();

        return this.ingredientCategoryRepository.findByName(ingredientCategoryName)
                .orElseThrow(() -> new ObjectNotFoundException(NAME_IDENTIFIER, ingredientCategory, INGREDIENT_CATEGORY));
    }
}
