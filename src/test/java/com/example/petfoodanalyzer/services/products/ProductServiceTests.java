package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.products.AddProductDTO;
import com.example.petfoodanalyzer.models.dtos.products.EditProductDTO;
import com.example.petfoodanalyzer.models.entities.ingredients.Ingredient;
import com.example.petfoodanalyzer.models.entities.ingredients.IngredientCategory;
import com.example.petfoodanalyzer.models.entities.products.Brand;
import com.example.petfoodanalyzer.models.entities.products.Pet;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.products.Review;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.enums.PetsTypes;
import com.example.petfoodanalyzer.models.viewModels.products.*;
import com.example.petfoodanalyzer.repositories.products.ProductRepository;
import com.example.petfoodanalyzer.services.ingredients.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    private ProductService testService;
    private Product firstProduct;
    private Product secondProduct;
    private Brand firstBrand;
    private Brand secondBrand;
    private Pet firstPet;
    private Ingredient firstIngredient;
    private Ingredient secondIngredient;

    @Mock
    private ProductRepository mockRepository;

    @Mock
    private BrandService mockBrandService;

    @Mock
    private PetService mockPetService;

    @Mock
    private IngredientService mockIngredientService;

    @Mock
    private ReviewService mockReviewService;

    @BeforeEach
    public void setup(){
        setupProducts();

        this.testService = new ProductService(mockRepository, new ModelMapper(),
                mockBrandService, mockPetService,
                mockIngredientService, mockReviewService);
    }

    private void setupProducts() {
        this.firstBrand = new Brand()
                .setName("Brand1");

        this.secondBrand = new Brand()
                .setName("Brand2");

        this.firstPet = new Pet()
                .setPetsType(PetsTypes.Cat);

        this.firstIngredient = new Ingredient()
                .setName("Ingredient1");

        this.firstProduct = new Product()
                .setName("Product1")
                .setBrand(firstBrand)
                .setPet(this.firstPet)
                .setReviews(new HashSet<>());

        this.firstProduct.getIngredients().add(this.firstIngredient);
        this.firstProduct.getReviews().add(new Review());

        this.secondIngredient = new Ingredient()
                .setName("Ingredient2");

        this.secondProduct = new Product()
                .setName("Product2")
                .setBrand(this.secondBrand)
                .setPet(this.firstPet)
                .setReviews(new HashSet<>());
    }

    @Test
    public void testGetProductById(){
        when(this.mockRepository.findById(1L))
                .thenReturn(Optional.ofNullable(this.firstProduct));

        Product result = this.testService.getProductById(1L);

        assertEquals(this.firstProduct, result);
        assertNotEquals(this.secondProduct, result);
    }

    @Test
    public void testGetProductByIdException(){
        when(this.mockRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable missingReview = assertThrows(ObjectNotFoundException.class, () -> this.testService.getProductById(1L));
        assertEquals("Object with id 1 and type Product not found!", missingReview.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    public void testGetProductByName(){
        when(this.mockRepository.findByName("Product1"))
                .thenReturn(Optional.ofNullable(this.firstProduct));

        Product result = this.testService.findByName("Product1");

        assertEquals(this.firstProduct, result);
        assertNotEquals(this.secondProduct, result);
    }

    @Test
    public void testGetProductByNameException(){
        when(this.mockRepository.findByName("Product1"))
                .thenReturn(Optional.empty());

        Throwable missingReview = assertThrows(ObjectNotFoundException.class, () -> this.testService.findByName("Product1"));
        assertEquals("Object with name Product1 and type Product not found!", missingReview.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    public void testAddProduct(){
        AddProductDTO dto = new AddProductDTO()
                .setName("New")
                .setDescription("Description")
                .setPicUrl("URL")
                .setBrandStr("Brand1")
                .setPetStr("Cat")
                .setIngredientsList("Ingredient1");

        when(this.mockBrandService.findByName("Brand1"))
                .thenReturn(this.firstBrand);

        when(this.mockPetService.getPetByName("Cat"))
                .thenReturn(this.firstPet);

        when(this.mockIngredientService.findByName("Ingredient1"))
                .thenReturn(this.firstIngredient);

        this.testService.addProduct(dto);

        verify(mockRepository).save(any());
    }

    @Test
    public void testGetAllProductsOnEmptyList(){
        List<ProductOverviewViewModel> result = this.testService.getAllProducts();
        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllProductsReturnsValidCount(){
        when(this.mockRepository.findAll()).thenReturn(List.of(this.firstProduct, this.secondProduct));

        List<ProductOverviewViewModel> result = this.testService.getAllProducts();

        assertEquals(2, result.size());
    }

    @Test
    public void testOverviewProductMappingCorrectly(){
        ProductOverviewViewModel result = this.testService.overviewMap(this.firstProduct);
        ProductOverviewViewModel result1 = this.testService.overviewMap(this.secondProduct);

        assertEquals(this.firstProduct.getName(), result.getName());
        assertEquals(this.secondProduct.getName(), result1.getName());

        assertEquals(this.firstProduct.getBrand().getName(), result.getBrandStr());
        assertEquals(this.secondProduct.getBrand().getName(), result1.getBrandStr());
    }

    @Test
    public void testGetProductDetailsByIdMappedDetailsCorrectly(){
        when(this.mockRepository.findById(1L)).thenReturn(Optional.ofNullable(this.firstProduct));

        UserEntity user = null;

        Set<ReviewInfoViewModel> reviews = new HashSet<>();
        reviews.add(new ReviewInfoViewModel());

        when(this.mockReviewService.mapReviewDetails(this.firstProduct.getReviews())).thenReturn(reviews);

        when(this.mockIngredientService.stringifyIngredientNames(this.firstProduct.getIngredients())).thenReturn("Ingredient1");

        ProductViewModel result = this.testService.getProductDetailsById(user, 1L);

        assertEquals(this.firstProduct.getName(), result.getName());
        assertEquals(this.firstProduct.getDescription(), result.getDescription());
        assertEquals(this.firstProduct.getBrand().getName(), result.getBrandStr());
        assertEquals(this.firstProduct.getPet().getPetsType().name(), result.getPetStr());
        assertEquals(this.firstProduct.getReviews().size(), result.getReviewsInfo().size());
        assertTrue(result.getIngredientsListed().contains("Ingredient1"));
        assertFalse(result.isLoggedUserFave());
    }

    @Test
    public void testGetProductDetailsFlagsFavoriteForLoggedUser(){
            when(this.mockRepository.findById(1L)).thenReturn(Optional.ofNullable(this.firstProduct));

            UserEntity user = new UserEntity();
            user.getFavorites().add(this.firstProduct);

            ProductViewModel result = this.testService.getProductDetailsById(user, 1L);

            assertTrue(result.isLoggedUserFave());
    }

    @Test
    public void getAllProductsByPetType(){
        when(this.mockRepository.findAllProductsByPetType(List.of(PetsTypes.Cat), 3L))
                .thenReturn(List.of(this.firstProduct, this.secondProduct));

        List<Product> result = this.testService.getAllProductsByPetType(3L, List.of(PetsTypes.Cat));

        assertEquals(2, result.size());
        assertEquals(this.firstProduct.getName(), result.get(0).getName());
        assertEquals(this.secondProduct.getName(), result.get(1).getName());
    }

    @Test
    public void testRecommendedProductMappingCorrectly(){
        RecommendedProductViewModel result = this.testService.recommendedMap(this.firstProduct);
        RecommendedProductViewModel result1 = this.testService.recommendedMap(this.secondProduct);

        assertEquals(this.firstProduct.getName(), result.getName(), "Incorrect recommended product name.");
        assertEquals(this.secondProduct.getName(), result1.getName(), "Incorrect recommended product name.");

        assertEquals(this.firstProduct.getPet().getPetsType().name(), result.getPetStr(), "Incorrect pet name for recommended product.");
        assertEquals(this.secondProduct.getPet().getPetsType().name(), result1.getPetStr(), "Incorrect pet name for recommended product.");

        assertEquals(this.firstProduct.getReviews().size(), result.getReviewsCount(), "Invalid review count for recommended product.");
        assertEquals(this.secondProduct.getReviews().size(), result1.getReviewsCount(), "Invalid review count for recommended product.");
    }


    @Test void getRecommendedProducts(){
        Product product3 = new Product().setName("3").setPet(this.firstPet).setBrand(this.firstBrand).setReviews(new HashSet<>());
        Product product4 = new Product().setName("4").setPet(this.firstPet).setBrand(this.firstBrand).setReviews(new HashSet<>());
        Product product5 = new Product().setName("5").setPet(this.firstPet).setBrand(this.firstBrand).setReviews(new HashSet<>());
        Product product6 = new Product().setName("6").setPet(this.firstPet).setBrand(this.firstBrand).setReviews(new HashSet<>());
        UserEntity user = new UserEntity();

        when(this.mockPetService.getUsersPetsTypes(user)).thenReturn(List.of(PetsTypes.Cat));

        when(this.mockRepository.findAllProductsByPetType(List.of(PetsTypes.Cat), 3L))
                .thenReturn(List.of(this.firstProduct, this.secondProduct, product3, product4, product5, product6));

        List<RecommendedProductViewModel> result = this.testService.getRecommendedProducts(user, 3L);

        assertEquals(5, result.size());
        assertEquals(this.firstProduct.getName(), result.get(0).getName());
        assertEquals(this.secondProduct.getPet().getPetsType().name(), result.get(1).getPetStr());
    }

    @Test
    public void testGetAllProductsByBrandOnEmptyList(){
        when(this.mockRepository.findByBrandId(1L)).thenReturn(new ArrayList<>());

        List<ProductOverviewViewModel> result = this.testService.getAllProductsByBrand(1L);

        assertEquals(0, result.size());
    }

    @Test
    public void testGetAllProductsByBrandReturnsCorrectCount(){
        when(this.mockRepository.findByBrandId(1L)).thenReturn(List.of(this.firstProduct, this.secondProduct));

        List<ProductOverviewViewModel> result = this.testService.getAllProductsByBrand(1L);

        assertEquals(2, result.size(), "Product by brand count not correct!");
    }

    @Test
    public void testGetEditedProductInfo(){
        when(mockRepository.findById(1L)).thenReturn(Optional.ofNullable(this.firstProduct));

        EditProductViewModel result = this.testService.getEditedProductInfo(1L);

        assertEquals(this.firstProduct.getName(), result.getName(), "Incorrect product name");
        assertEquals(this.firstProduct.getBrand().getName(), result.getBrandStr(), "Incorrect product brand name");
        assertEquals(this.firstProduct.getPet().getPetsType().name(), result.getPetStr(), "Incorrect product pet name");
    }

    @Test
    public void testEditProductCallsSaveOnEmptyProduct(){
        when(this.mockRepository.findById(1L)).thenReturn(Optional.ofNullable(this.firstProduct));

        when(this.mockBrandService.findByName("Brand1"))
                .thenReturn(this.firstBrand);

        when(this.mockPetService.getPetByName("Cat"))
                .thenReturn(this.firstPet);

        EditProductDTO dto = new EditProductDTO()
                .setName("")
                .setDescription("")
                .setPicUrl("")
                .setBrandStr("Brand1")
                .setPetStr("Cat")
                .setIngredientsList("");

        this.testService.editProduct(1L, dto);

        verify(mockRepository).save(any());
    }

    @Test
    public void testEditProductUpdatesProductValues(){
        when(this.mockRepository.findById(1L)).thenReturn(Optional.ofNullable(this.firstProduct));

        when(this.mockBrandService.findByName("Brand2"))
                .thenReturn(this.secondBrand);

        when(this.mockPetService.getPetByName("Cat"))
                .thenReturn(this.firstPet);

        when(this.mockIngredientService.findByName("Ingredient1")).thenReturn(this.firstIngredient);
        when(this.mockIngredientService.findByName("Ingredient2")).thenReturn(this.secondIngredient);

        EditProductDTO dto = new EditProductDTO()
                .setName("New")
                .setDescription("Test")
                .setPicUrl("NewURL")
                .setBrandStr("Brand2")
                .setPetStr("Cat")
                .setIngredientsList("Ingredient1, Ingredient2");

        this.testService.editProduct(1L, dto);

        assertEquals(dto.getName(), this.firstProduct.getName(), "Product name not updated after edit!");
        assertEquals(dto.getDescription(), this.firstProduct.getDescription(), "Product description not updated after edit!");
        assertEquals(dto.getPicUrl(), this.firstProduct.getPicUrl(), "Product pic URL not updated after edit!");
        assertEquals(dto.getBrandStr(), this.firstProduct.getBrand().getName(), "Product brand not updated after edit!");
        assertEquals(2, this.firstProduct.getIngredients().size());

    }
}
