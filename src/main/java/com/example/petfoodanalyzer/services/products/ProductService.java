package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.*;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.models.entities.ingredients.Ingredient;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.products.Review;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.models.viewModels.products.EditProductViewModel;
import com.example.petfoodanalyzer.models.viewModels.products.ProductViewModel;
import com.example.petfoodanalyzer.models.viewModels.products.ProductOverviewViewModel;
import com.example.petfoodanalyzer.models.viewModels.products.RecommendedProductViewModel;
import com.example.petfoodanalyzer.repositories.products.ProductRepository;
import com.example.petfoodanalyzer.services.ingredients.IngredientService;
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

    @Autowired
    public ProductService(ProductRepository productRepository, ModelMapper modelMapper, BrandService brandService, PetService petService, IngredientService ingredientService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.brandService = brandService;
        this.petService = petService;
        this.ingredientService = ingredientService;
    }

    public Product getProductById(Long id) {
        return this.productRepository.findById(id).get();
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

    public List<ProductOverviewViewModel> getAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(this::overviewMap)
                .collect(Collectors.toList());
    }

    private ProductOverviewViewModel overviewMap(Product product) {
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

        Set<ReviewInfoDTO> reviews = mapReviewDetails(user, product.getReviews());
        productDetails.setReviewsInfo(reviews);

        return productDetails;
    }

    //Initially placed these methods in review service as they are working with the review-related objects
    //Moved them here as they do not really use review repository and to avoid circular references between review service and product service

    public Set<ReviewInfoDTO> mapReviewDetails(UserEntity user, Set<Review> reviews) {
        return reviews.stream()
                .map(r -> map(user, r))
                .collect(Collectors.toSet());
    }

    private ReviewInfoDTO map(UserEntity user, Review review) {
        ReviewInfoDTO reviewInfo = this.modelMapper.map(review, ReviewInfoDTO.class);

        reviewInfo.setAuthorUsername(review.getAuthor().getDisplayName());
        reviewInfo.setAuthorProfilePic(review.getAuthor().getProfilePicUrl());

        reviewInfo.setLikesCount(review.getLikes().size());

        if (review.getLikes().contains(user)) {
            reviewInfo.setLoggedUserLike(true);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = review.getCreatedOn().format(formatter);
        reviewInfo.setCreated(formatDateTime);

        return reviewInfo;
    }
    public List<RecommendedProductViewModel> getRecommendedProducts(UserEntity userEntity, Long productId) {
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

    public RecommendedProductViewModel recommendedMap(Product product){
        RecommendedProductViewModel recommended = this.modelMapper.map(product, RecommendedProductViewModel.class);

        recommended.setPetStr(product.getPet().getPetsType().name());
        recommended.setReviewsCount(product.getReviews().size());

        return recommended;
    }

    public List<ProductOverviewViewModel> getAllProductsByBrand(Long id) {
        return this.productRepository.findByBrandId(id)
                .stream()
                .map(p -> this.modelMapper.map(p, ProductOverviewViewModel.class))
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
}
