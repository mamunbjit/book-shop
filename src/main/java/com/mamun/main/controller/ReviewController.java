package com.mamun.main.controller;


import com.mamun.main.dtoClasses.ReviewDTO;
import com.mamun.main.entity.ReviewEntity;
import com.mamun.main.response.CustomResponse;
import com.mamun.main.services.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class ReviewController {
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewController(ReviewService reviewService, ModelMapper modelMapper){
        this.reviewService=reviewService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/review/{bookId}/{userId}/create")
    public ResponseEntity<ReviewEntity> createReview(
            @PathVariable Long bookId,
            @PathVariable Long userId,
            @RequestBody ReviewEntity reviewEntity
    ) {
        ReviewEntity createdReview = reviewService.createReview(bookId, userId, reviewEntity);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }




    @GetMapping("/{bookId}/reviews")
    public ResponseEntity<List<ReviewDTO>> getBookReviews(@PathVariable Long bookId) {
        List<ReviewDTO> reviews = reviewService.getBookReviews(bookId);

        if (!reviews.isEmpty()) {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/{reviewId}/{userId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable Long reviewId,
            @PathVariable Long userId
    ) {
        boolean deleted = reviewService.deleteUserReview(reviewId, userId);
        if (deleted) {
            return new ResponseEntity<>(new CustomResponse("Successfully deleted"),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}

