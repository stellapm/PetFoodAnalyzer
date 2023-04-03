package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.exceptions.ObjectNotFoundException;
import com.example.petfoodanalyzer.models.dtos.products.AddReviewDTO;
import com.example.petfoodanalyzer.models.viewModels.products.ReviewInfoViewModel;
import com.example.petfoodanalyzer.models.viewModels.products.ReviewOverviewViewModel;
import com.example.petfoodanalyzer.models.entities.products.Product;
import com.example.petfoodanalyzer.models.entities.products.Review;
import com.example.petfoodanalyzer.models.entities.users.UserEntity;
import com.example.petfoodanalyzer.repositories.products.ReviewRepository;
import com.example.petfoodanalyzer.services.users.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.petfoodanalyzer.constants.Exceptions.ID_IDENTIFIER;
import static com.example.petfoodanalyzer.constants.Models.REVIEW;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final UserEntityService userEntityService;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper, UserEntityService userEntityService) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.userEntityService = userEntityService;
    }

    public Review getReviewById(Long reviewId) {
        return this.reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ObjectNotFoundException(ID_IDENTIFIER, String.valueOf(reviewId), REVIEW));
    }

    public void saveReview(Product product, AddReviewDTO addReviewDTO, String email) {
        Review review = this.modelMapper.map(addReviewDTO, Review.class);

        UserEntity author = this.userEntityService.findByEmail(email);
        review.setAuthor(author);

        review.setCreatedOn(LocalDateTime.now());
        review.setProduct(product);

        this.reviewRepository.save(review);
    }

    public List<ReviewOverviewViewModel> findMostReviewed() {
        return this.reviewRepository.findMostReviewed()
                .stream()
                .limit(3)
                .map(r -> new ReviewOverviewViewModel(
                        r.getAuthor().getDisplayName(),
                        r.getAuthor().getProfilePicUrl(),
                        r.getContent(),
                        r.getProduct().getName(),
                        r.getProduct().getPicUrl(),
                        r.getProduct().getId()
                ))
                .toList();
    }

    public void reportReview(Long id) {
        Review review = getReviewById(id);
        review.setReported(true);
        this.reviewRepository.save(review);
    }

    public List<Review> getReportedReviews() {
        return this.reviewRepository.findAllReported();
    }

    public void cleanUpReported() {
        this.reviewRepository.deleteAll(getReportedReviews());
    }

    public Set<ReviewInfoViewModel> mapReviewDetails(Set<Review> reviews) {
        return reviews.stream()
                .map(this::mapReviewToInfoModel)
                .collect(Collectors.toSet());
    }

    public ReviewInfoViewModel mapReviewToInfoModel(Review review) {
        ReviewInfoViewModel reviewInfo = this.modelMapper.map(review, ReviewInfoViewModel.class);

        reviewInfo.setAuthorUsername(review.getAuthor().getDisplayName());
        reviewInfo.setAuthorProfilePic(review.getAuthor().getProfilePicUrl());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDateTime = review.getCreatedOn().format(formatter);
        reviewInfo.setCreated(formatDateTime);

        return reviewInfo;
    }
}
