package com.example.petfoodanalyzer.services.products;

import com.example.petfoodanalyzer.models.dtos.products.ReviewInfoDTO;
import com.example.petfoodanalyzer.models.entities.products.Review;
import com.example.petfoodanalyzer.repositories.products.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

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

        return reviewInfo;
    }
}
