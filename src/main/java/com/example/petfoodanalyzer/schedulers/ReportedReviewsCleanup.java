package com.example.petfoodanalyzer.schedulers;

import com.example.petfoodanalyzer.services.products.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReportedReviewsCleanup {
    private final ReviewService reviewService;

    @Autowired
    public ReportedReviewsCleanup(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void reviewCleanup(){
        this.reviewService.cleanUpReported();
    }
}
