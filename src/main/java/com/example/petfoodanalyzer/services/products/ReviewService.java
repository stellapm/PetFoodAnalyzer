package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.AddReviewDTO;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.products.Review;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.repositories.products.ReviewRepository;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserEntityService userEntityService;
    private final ProductService productService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper, UserEntityService userEntityService, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.userEntityService = userEntityService;
        this.productService = productService;
    }

    public void saveReview(Long id, AddReviewDTO addReviewDTO, String email) {
        Review review = this.modelMapper.map(addReviewDTO, Review.class);

        UserEntity author = this.userEntityService.findByEmail(email);
        review.setAuthor(author);

        review.setCreatedOn(LocalDateTime.now());

        Product product = this.productService.getProductById(id);
        review.setProduct(product);

        this.reviewRepository.save(review);
    }

    public void likeProductReview(Long id, String username) {
        Review review = this.reviewRepository.findById(id).get();

        UserEntity user = this.userEntityService.findByEmail(username);
        review.getLikes().add(user);

        this.reviewRepository.save(review);
    }
}
