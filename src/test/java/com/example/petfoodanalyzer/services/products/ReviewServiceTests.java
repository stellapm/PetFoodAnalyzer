package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.products.AddReviewDTO;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.products.Review;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.models.viewModels.products.ReviewInfoViewModel;
import com.example.petfoodanalyzer.models.viewModels.products.ReviewOverviewViewModel;
import com.example.petfoodanalyzer.repositories.products.ReviewRepository;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {
    private ReviewService testService;
    private Review first;
    private Review second;
    private UserEntity user1;
    private UserEntity user2;
    private Product product1;
    private Product product2;

    @Mock
    private ReviewRepository mockRepository;

    @Mock
    private UserEntityService mockUserEntityService;

    @BeforeEach
    public void setup(){
        setupReviews();

        this.testService = new ReviewService(this.mockRepository, new ModelMapper(), this.mockUserEntityService);
    }

    private void setupReviews() {
        this.user1 = new UserEntity()
                .setEmail("example1@abv.bg")
                .setDisplayName("display1")
                .setProfilePicUrl("UserURL1");

        this.product1 = new Product()
                .setName("Name1")
                .setPicUrl("ProductURL1");

        this.product1.setId(1L);

        this.first = new Review()
                .setAuthor(user1)
                .setProduct(product1)
                .setCreatedOn(LocalDateTime.now())
                .setReported(true)
                .setContent("Content")
                .setLikes(new HashSet<>());

        this.user2 = new UserEntity()
                .setEmail("example2@abv.bg")
                .setDisplayName("display2")
                .setProfilePicUrl("UserURL2");

        this.product2 = new Product()
                .setName("Name2")
                .setPicUrl("ProductURL2");

        this.product2.setId(2L);

        this.second = new Review()
                .setAuthor(user2)
                .setProduct(product2)
                .setCreatedOn(LocalDateTime.now())
                .setReported(false)
                .setContent("Content2")
                .setLikes(new HashSet<>());
    }

    @Test
    public void testGetReviewByIdAndProductId(){
        when(this.mockRepository.findByIdAndProductId(1L, 3L))
                .thenReturn(Optional.ofNullable(this.first));

        Review result = this.testService.getReviewByIdAndProductId(1L, 3L);

        assertEquals(this.first, result);
        assertNotEquals(this.second, result);
    }

    @Test
    public void testGetReviewByIdAndProductIdException(){
        when(this.mockRepository.findByIdAndProductId(1L, 3L))
                .thenReturn(Optional.empty());

        Throwable missingReview = assertThrows(ObjectNotFoundException.class, () -> this.testService.getReviewByIdAndProductId(1L, 3L));
        assertEquals("Object with id 1 or 3 and type Review or product not found!", missingReview.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    public void testSaveReview(){
        AddReviewDTO addReviewDTO = new AddReviewDTO()
                .setContent("Example");

        when(mockUserEntityService.findByEmail("example1@abv.bg")).thenReturn(this.user1);
//        when(mockProductService.getProductById(1L)).thenReturn(this.product1);

        this.testService.saveReview(this.product1, addReviewDTO, "example1@abv.bg");

        verify(mockRepository).save(any());
    }

    @Test
    public void testLikeReview(){
        when(this.mockRepository.findByIdAndProductId(1L, 3L))
                .thenReturn(Optional.ofNullable(this.first));

        when(mockUserEntityService.findByEmail("example1@abv.bg")).thenReturn(this.user1);

        this.testService.likeProductReview(1L,"example1@abv.bg", 3L);

        verify(mockRepository).save(any());
    }

    @Test
    public void testFindMostReviewed(){
        when(this.mockRepository.findMostReviewed()).thenReturn(List.of(this.first, this.second));

        List<ReviewOverviewViewModel> result = this.testService.findMostReviewed();

        assertEquals(2, result.size());

        ReviewOverviewViewModel model1 = result.get(0);
        ReviewOverviewViewModel model2 = result.get(1);

        assertEquals(model1.getProductName(), this.first.getProduct().getName());
        assertEquals(model1.getContent(), this.first.getContent());
        assertEquals(model1.getAuthorDisplayName(), this.first.getAuthor().getDisplayName());

        assertNotEquals(model2.getProductName(), this.first.getProduct().getName());
        assertNotEquals(model2.getContent(), this.first.getContent());
        assertNotEquals(model2.getAuthorDisplayName(), this.first.getAuthor().getDisplayName());

        assertEquals(model2.getProductName(), this.second.getProduct().getName());
        assertEquals(model2.getContent(), this.second.getContent());
        assertEquals(model2.getAuthorDisplayName(), this.second.getAuthor().getDisplayName());
    }

    @Test
    public void testReportReview(){
        when(this.mockRepository.findByIdAndProductId(1L, 3L))
                .thenReturn(Optional.ofNullable(this.first));

        this.testService.reportReview(1L, 3L);

        verify(mockRepository).save(any());
    }

    @Test
    public void testGerAllReportedReviews(){
        when(this.mockRepository.findAllReported()).thenReturn(List.of(this.first));

        List<Review> result = this.testService.getReportedReviews();

        assertEquals(1, result.size());
        assertTrue(result.contains(this.first));
    }

    @Test
    public void testCleanUpReported(){
        when(this.mockRepository.findAllReported()).thenReturn(List.of(this.first));
        this.testService.cleanUpReported();

        verify(this.mockRepository).deleteAll(any());
    }

    @Test
    public void testMapReviewToInfoModelNoLikes(){
        ReviewInfoViewModel result = this.testService.mapReviewToInfoModel(this.user1, this.first);

        assertEquals(this.first.getAuthor().getDisplayName(), result.getAuthorUsername());
        assertEquals(this.first.getAuthor().getProfilePicUrl(), result.getAuthorProfilePic());
        assertEquals(this.first.getContent(), result.getContent(), "Review content not correct!");
        assertEquals(0, result.getLikesCount());
        assertFalse(result.isLoggedUserLike());
    }

    @Test
    public void testMapReviewToInfoModelLikesCheck(){
        this.first.getLikes().add(this.user1);

        ReviewInfoViewModel result = this.testService.mapReviewToInfoModel(this.user1, this.first);

        assertEquals(1, result.getLikesCount());
        assertTrue(result.isLoggedUserLike());
    }

    @Test
    public void testMapReviewDetails(){
        Set<ReviewInfoViewModel> result = this.testService.mapReviewDetails(this.user1, Set.of(this.first, this.second));

        assertEquals(2, result.size());
    }
}
