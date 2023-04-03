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
    private Review firstReview;
    private Review secondReview;
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

        this.firstReview = new Review()
                .setAuthor(this.user1)
                .setProduct(this.product1)
                .setCreatedOn(LocalDateTime.now())
                .setReported(true)
                .setContent("Content");

        this.user2 = new UserEntity()
                .setEmail("example2@abv.bg")
                .setDisplayName("display2")
                .setProfilePicUrl("UserURL2");

        this.product2 = new Product()
                .setName("Name2")
                .setPicUrl("ProductURL2");

        this.product2.setId(2L);

        this.secondReview = new Review()
                .setAuthor(user2)
                .setProduct(product2)
                .setCreatedOn(LocalDateTime.now())
                .setReported(false)
                .setContent("Content2");
    }

    @Test
    public void testGetReviewByIdAndProductId(){
        when(this.mockRepository.findById(1L))
                .thenReturn(Optional.ofNullable(this.firstReview));

        Review result = this.testService.getReviewById(1L);

        assertEquals(this.firstReview, result);
        assertNotEquals(this.secondReview, result);
    }

    @Test
    public void testGetReviewByIdAndProductIdException(){
        when(this.mockRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable missingReview = assertThrows(ObjectNotFoundException.class, () -> this.testService.getReviewById(1L));
        assertEquals("Object with id 1 and type Review not found!", missingReview.getMessage(), "Incorrect error message thrown.");
    }

    @Test
    public void testSaveReview(){
        AddReviewDTO addReviewDTO = new AddReviewDTO()
                .setContent("Example");

        when(mockUserEntityService.findByEmail("example1@abv.bg")).thenReturn(this.user1);

        this.testService.saveReview(this.product1, addReviewDTO, "example1@abv.bg");

        verify(mockRepository).save(any());
    }

    @Test
    public void testFindMostReviewed(){
        when(this.mockRepository.findMostReviewed()).thenReturn(List.of(this.firstReview, this.secondReview));

        List<ReviewOverviewViewModel> result = this.testService.findMostReviewed();

        assertEquals(2, result.size());

        ReviewOverviewViewModel model1 = result.get(0);
        ReviewOverviewViewModel model2 = result.get(1);

        assertEquals(model1.getProductName(), this.firstReview.getProduct().getName());
        assertEquals(model1.getContent(), this.firstReview.getContent());
        assertEquals(model1.getAuthorDisplayName(), this.firstReview.getAuthor().getDisplayName());

        assertNotEquals(model2.getProductName(), this.firstReview.getProduct().getName());
        assertNotEquals(model2.getContent(), this.firstReview.getContent());
        assertNotEquals(model2.getAuthorDisplayName(), this.firstReview.getAuthor().getDisplayName());

        assertEquals(model2.getProductName(), this.secondReview.getProduct().getName());
        assertEquals(model2.getContent(), this.secondReview.getContent());
        assertEquals(model2.getAuthorDisplayName(), this.secondReview.getAuthor().getDisplayName());
    }

    @Test
    public void testReportReview(){
        when(this.mockRepository.findById(1L))
                .thenReturn(Optional.ofNullable(this.firstReview));

        this.testService.reportReview(1L);

        verify(mockRepository).save(any());
    }

    @Test
    public void testGerAllReportedReviews(){
        when(this.mockRepository.findAllReported()).thenReturn(List.of(this.firstReview));

        List<Review> result = this.testService.getReportedReviews();

        assertEquals(1, result.size());
        assertTrue(result.contains(this.firstReview));
    }

    @Test
    public void testCleanUpReported(){
        when(this.mockRepository.findAllReported()).thenReturn(List.of(this.firstReview));
        this.testService.cleanUpReported();

        verify(this.mockRepository).deleteAll(any());
    }

    @Test
    public void testMapReviewToInfoModelNoLikes(){
        ReviewInfoViewModel result = this.testService.mapReviewToInfoModel(this.firstReview);

        assertEquals(this.firstReview.getAuthor().getDisplayName(), result.getAuthorUsername());
        assertEquals(this.firstReview.getAuthor().getProfilePicUrl(), result.getAuthorProfilePic());
        assertEquals(this.firstReview.getContent(), result.getContent(), "Review content not correct!");
    }

    @Test
    public void testMapReviewDetails(){
        Set<ReviewInfoViewModel> result = this.testService.mapReviewDetails(Set.of(this.firstReview, this.secondReview));

        assertEquals(2, result.size());
    }
}
