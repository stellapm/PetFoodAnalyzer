package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.IngredientCategoryInitDTO;
import com.example.petfoodanalyzer.models.entities.products.IngredientCategory;
import com.example.petfoodanalyzer.models.enums.IngredientCategoryNames;
import com.example.petfoodanalyzer.repositories.products.IngredientCategoryRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

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
                .map(cn -> new IngredientCategory(getCategoryName(cn.getCode()), cn.getDescription()))
                .toList();

        this.ingredientCategoryRepository.saveAll(categoryNames);
    }

    private IngredientCategoryNames getCategoryName(String code){
        return IngredientCategoryNames.valueOf(code);
    }
}
