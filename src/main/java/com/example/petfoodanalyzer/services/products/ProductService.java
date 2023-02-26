package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.AddProductDTO;
import com.example.petfoodanalyzer.models.dtos.products.ProductDetailsDTO;
import com.example.petfoodanalyzer.models.dtos.products.ProductOverviewInfoDTO;
import com.example.petfoodanalyzer.models.dtos.products.ReviewInfoDTO;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.models.entities.ingredients.Ingredient;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.repositories.products.ProductRepository;
import com.example.petfoodanalyzer.services.ingredients.IngredientService;
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
    private final ReviewService reviewService;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, BrandService brandService, PetService petService, IngredientService ingredientService, ReviewService reviewService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.brandService = brandService;
        this.petService = petService;
        this.ingredientService = ingredientService;
        this.reviewService = reviewService;
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

    public List<ProductOverviewInfoDTO> getAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    private ProductOverviewInfoDTO map(Product product) {
        ProductOverviewInfoDTO productInfo = this.modelMapper.map(product, ProductOverviewInfoDTO.class);

        productInfo.setBrandStr(product.getBrand().getName());
        productInfo.setPetStr(product.getPet().getPetsType().name());

        return productInfo;
    }

    public ProductDetailsDTO findById(Long id) {
        Product product = this.productRepository.findById(id).get();

        ProductDetailsDTO productDetails = this.modelMapper.map(product, ProductDetailsDTO.class);

        productDetails.setBrandStr(product.getBrand().getName());
        productDetails.setPetStr(product.getPet().getPetsType().name());

        String ingredientsList = ingredientService.stringifyIngredientNames(product.getIngredients());
        productDetails.setIngredientsListed(ingredientsList);

        Set<ReviewInfoDTO> reviews = this.reviewService.mapReviewDetails(product.getReviews());
        productDetails.setReviewsInfo(reviews);

        return productDetails;
    }
}
