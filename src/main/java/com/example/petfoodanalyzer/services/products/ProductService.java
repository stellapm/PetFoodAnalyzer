package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.*;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.models.entities.ingredients.Ingredient;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.products.Review;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.repositories.products.ProductRepository;
import com.example.petfoodanalyzer.services.ingredients.IngredientService;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final UserEntityService userEntityService;

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, BrandService brandService, PetService petService, IngredientService ingredientService, UserEntityService userEntityService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.brandService = brandService;
        this.petService = petService;
        this.ingredientService = ingredientService;
        this.userEntityService = userEntityService;
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
                .map(this::overviewMap)
                .collect(Collectors.toList());
    }

    private ProductOverviewInfoDTO overviewMap(Product product) {
        ProductOverviewInfoDTO productInfo = this.modelMapper.map(product, ProductOverviewInfoDTO.class);

        productInfo.setBrandStr(product.getBrand().getName());
        productInfo.setPetStr(product.getPet().getPetsType().name());

        return productInfo;
    }

    public ProductDetailsDTO getProductDetailsById(Long id) {
        Product product = getProductById(id);

        ProductDetailsDTO productDetails = this.modelMapper.map(product, ProductDetailsDTO.class);

        productDetails.setBrandStr(product.getBrand().getName());
        productDetails.setPetStr(product.getPet().getPetsType().name());

        String ingredientsList = ingredientService.stringifyIngredientNames(product.getIngredients());
        productDetails.setIngredientsListed(ingredientsList);

        Set<ReviewInfoDTO> reviews = mapReviewDetails(product.getReviews());
        productDetails.setReviewsInfo(reviews);

        return productDetails;
    }

    public Product getProductById(Long id) {
        return this.productRepository.findById(id).get();
    }

    public List<RecommendedProductDTO> getRecommendedProducts(UserEntity userEntity, Long productId) {
        List<PetsTypes> pets = new ArrayList<>();

        if (userEntity == null || userEntity.getPets().size() == 0){
            pets = Arrays.stream(PetsTypes.values()).toList();
        } else {
            userEntity.getPets().stream()
                    .map(Pet::getPetsType)
                    .forEach(pets::add);
        }

        List<Product> products = this.productRepository.findAllProductsByPetType(pets, productId);

        return products.stream()
                .limit(5)
                .map(this::recommendedMap)
                .toList();
    }

    public RecommendedProductDTO recommendedMap(Product product){
        RecommendedProductDTO recommended = this.modelMapper.map(product, RecommendedProductDTO.class);

        recommended.setPetStr(product.getPet().getPetsType().name());
        recommended.setReviewsCount(product.getReviews().size());

        return recommended;
    }

    //Initially placed these methods in review service as they are working with the review-related objects
    //Moved them here as they do not really use review repository and to avoid circular references between review service and product service

    public Set<ReviewInfoDTO> mapReviewDetails(Set<Review> reviews) {
        return reviews.stream()
                .map(this::map)
                .collect(Collectors.toSet());
    }

    private ReviewInfoDTO map(Review review) {
        ReviewInfoDTO reviewInfo = this.modelMapper.map(review, ReviewInfoDTO.class);
        reviewInfo.setAuthorUsername(review.getAuthor().getDisplayName());
        reviewInfo.setAuthorProfilePic(review.getAuthor().getProfilePicUrl());
        reviewInfo.setLikesCount(review.getLikes().size());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = review.getCreatedOn().format(formatter);
        reviewInfo.setCreated(formatDateTime);

        return reviewInfo;
    }
}
