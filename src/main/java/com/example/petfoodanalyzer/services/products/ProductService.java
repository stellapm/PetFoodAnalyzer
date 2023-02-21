package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.AddProductDTO;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.models.entities.products.Ingredient;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.users.Pet;
import com.example.petfoodanalyzer.repositories.products.ProductRepository;
import com.example.petfoodanalyzer.services.users.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final BrandService brandService;
    private final PetService petService;
    private final IngredientService ingredientService;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, BrandService brandService, PetService petService, IngredientService ingredientService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.brandService = brandService;
        this.petService = petService;
        this.ingredientService = ingredientService;
    }

    public Product findByName(String name) {
        return this.productRepository.findByName(name);
    }

    public void addProduct(AddProductDTO addProductDTO) {
        Product product = this.modelMapper.map(addProductDTO, Product.class);

        Brand brand = this.brandService.findByName(addProductDTO.getBrandStr());
        product.setBrand(brand);

        Pet pet = this.petService.getPetByName(addProductDTO.getPetStr());
        product.setPet(pet);

        List<String> ingredientsRaw = Arrays.stream(addProductDTO.getIngredientsList().split(",\\s+")).toList();

        Set<Ingredient> ingredients = ingredientsRaw.stream()
                .map(this.ingredientService::findByName)
                .collect(Collectors.toSet());

        product.setIngredients(ingredients);

        this.productRepository.save(product);
    }
}
