package com.example.petfoodanalyzer.web.controllers;

import com.example.petfoodanalyzer.models.dtos.products.AddReviewDTO;
import com.example.petfoodanalyzer.services.products.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reviews")
public class ReviewController extends BaseController{
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @ModelAttribute(name = "addReviewDTO")
    public AddReviewDTO addReviewDTO() {
        return new AddReviewDTO();
    }


    @GetMapping("/{productId}/like-review/{reviewId}")
    public ModelAndView likeProductReview(@PathVariable Long reviewId, @PathVariable Long productId) {
        UserDetails user = getCurrentUserDetails();

        this.reviewService.likeProductReview(reviewId, user.getUsername(), productId);

        return super.redirect("/products/details/" + productId);
    }

    @GetMapping("/{productId}/report-review/{reviewId}")
    public ModelAndView reportProductReview(@PathVariable Long reviewId, @PathVariable Long productId) {

        this.reviewService.reportReview(reviewId, productId);

        return super.redirect("/products/details/" + productId);
    }

    @PostMapping("/post-review/{productId}")
    public ModelAndView postProductReview(@PathVariable Long productId,
                                          @Valid AddReviewDTO addReviewDTO,
                                          BindingResult bindingResult,
                                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addReviewDTO", addReviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addReviewDTO", bindingResult);

            return super.redirect("/products/details/" + productId);
        }

        UserDetails user = getCurrentUserDetails();
        this.reviewService.saveReview(productId, addReviewDTO, user.getUsername());

        return super.redirect("/products/details/" + productId);
    }
}
