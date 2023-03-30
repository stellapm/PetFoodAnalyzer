package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.products.*;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.models.entities.ingredients.Ingredient;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.products.Review;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.models.viewModels.products.*;
import com.example.petfoodanalyzer.repositories.products.ProductRepository;
import com.example.petfoodanalyzer.services.ingredients.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sound.sampled.ReverbType;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.petfoodanalyzer.constants.Exceptions.ID_IDENTIFIER;
import static com.example.petfoodanalyzer.constants.Exceptions.NAME_IDENTIFIER;
import static com.example.petfoodanalyzer.constants.Models.PRODUCT;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final BrandService brandService;
    private final PetService petService;
    private final IngredientService ingredientService;
    private final ReviewService reviewService;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper,
                          BrandService brandService, PetService petService,
                          IngredientService ingredientService, ReviewService reviewService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.brandService = brandService;
        this.petService = petService;
        this.ingredientService = ingredientService;
        this.reviewService = reviewService;
    }

    public Product getProductById(Long id) {
        return this.productRepository
                .findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ID_IDENTIFIER, String.valueOf(id), PRODUCT));
    }

    public Product findByName(String name) {
        return this.productRepository
                .findByName(name)
                .orElseThrow(() -> new ObjectNotFoundException(NAME_IDENTIFIER, name, PRODUCT));
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

    public List<ProductOverviewViewModel> getAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(this::overviewMap)
                .collect(Collectors.toList());
    }

    public ProductOverviewViewModel overviewMap(Product product) {
        ProductOverviewViewModel productInfo = this.modelMapper.map(product, ProductOverviewViewModel.class);

        productInfo.setBrandStr(product.getBrand().getName());
        productInfo.setPetStr(product.getPet().getPetsType().name());

        return productInfo;
    }

    public ProductViewModel getProductDetailsById(UserEntity user, Long id) {
        Product product = getProductById(id);

        ProductViewModel productDetails = this.modelMapper.map(product, ProductViewModel.class);

        productDetails.setBrandStr(product.getBrand().getName());
        productDetails.setPetStr(product.getPet().getPetsType().name());

        String ingredientsList = ingredientService.stringifyIngredientNames(product.getIngredients());
        productDetails.setIngredientsListed(ingredientsList);

        if (user!= null && user.getFavorites().contains(product)){
            productDetails.setLoggedUserFave(true);
        }

        Set<ReviewInfoViewModel> reviews = this.reviewService.mapReviewDetails(user, product.getReviews());
        productDetails.setReviewsInfo(reviews);

        return productDetails;
    }

    public List<RecommendedProductViewModel> getRecommendedProducts(UserEntity userEntity, Long productId) {
        List<PetsTypes> pets = this.petService.getUsersPetsTypes(userEntity);

        List<Product> products = getAllProductsByPetType(productId, pets);

        return products.stream()
                .limit(5)
                .map(this::recommendedMap)
                .toList();
    }

    public List<Product> getAllProductsByPetType(Long productId, List<PetsTypes> pets) {
        return this.productRepository.findAllProductsByPetType(pets, productId);
    }

    public RecommendedProductViewModel recommendedMap(Product product){
        RecommendedProductViewModel recommended = this.modelMapper.map(product, RecommendedProductViewModel.class);

        recommended.setPetStr(product.getPet().getPetsType().name());
        recommended.setReviewsCount(product.getReviews().size());

        return recommended;
    }

    public List<ProductOverviewViewModel> getAllProductsByBrand(Long id) {
        return this.productRepository.findByBrandId(id)
                .stream()
                .map(this::overviewMap)
                .toList();
    }

    public EditProductViewModel getEditedProductInfo(Long id) {
        Product product = this.getProductById(id);

        EditProductViewModel editView = this.modelMapper.map(product, EditProductViewModel.class);

        editView.setBrandStr(product.getBrand().getName());
        editView.setPetStr(product.getPet().getPetsType().name());
        editView.setIngredientsList(this.ingredientService.stringifyIngredientNames(product.getIngredients()));

        return editView;
    }

    public void editProduct(Long id, EditProductDTO editProductDTO) {
        Product product = this.getProductById(id);

        if (!editProductDTO.getName().trim().isBlank()){
            product.setName(editProductDTO.getName());
        }

        if (!editProductDTO.getDescription().trim().isBlank()){
            product.setDescription(editProductDTO.getDescription());
        }

        if (!editProductDTO.getPicUrl().trim().isBlank()){
            product.setPicUrl(editProductDTO.getPicUrl());
        }

        Brand brand = this.brandService.findByName(editProductDTO.getBrandStr());
        product.setBrand(brand);

        Pet pet = this.petService.getPetByName(editProductDTO.getPetStr());
        product.setPet(pet);

        if (!editProductDTO.getIngredientsList().trim().isBlank()){
            List<String> ingredientsRaw = Arrays.stream(editProductDTO.getIngredientsList().split(",\\s+")).toList();

            Set<Ingredient> ingredients = ingredientsRaw.stream()
                    .map(this.ingredientService::findByName)
                    .collect(Collectors.toSet());

            product.setIngredients(ingredients);
        }

        this.productRepository.save(product);
    }
}
