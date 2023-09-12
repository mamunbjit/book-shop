package com.mamun.main.services;



import com.mamun.main.dtoClasses.ReviewDTO;
import com.mamun.main.entity.BookEntity;
import com.mamun.main.entity.ReviewEntity;
import com.mamun.main.entity.UserEntity;
import com.mamun.main.repository.BookRepository;
import com.mamun.main.repository.ReviewRepository;
import com.mamun.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewService(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            BookRepository bookRepository,
            ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.modelMapper=modelMapper;
    }

    public ReviewEntity createReview(Long bookId, Long userId, ReviewEntity reviewEntity) {
        // Check if the user and book exist
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        Optional<BookEntity> bookOptional = bookRepository.findById(bookId);

        if (userOptional.isPresent() && bookOptional.isPresent()) {
            UserEntity user = userOptional.get();
            BookEntity book = bookOptional.get();

            // Set the user and book in the review entity
            reviewEntity.setUser(user);
            reviewEntity.setBook(book);

            // Save the review entity
            reviewEntity = reviewRepository.save(reviewEntity);

            // Calculate and update the book's rating
            double updatedRating = calculateUpdatedRating(book);
            book.setRating(updatedRating);
            bookRepository.save(book);

            return reviewEntity;
        } else {
            // User or book not found, handle the error as needed
            return null;
        }
    }

    private double calculateUpdatedRating(BookEntity book) {
        // Retrieve all reviews for the book
        List<ReviewEntity> bookReviews = book.getReviews();

        if (bookReviews.isEmpty()) {
            return 0.0; // No reviews yet, set the rating to 0
        }

        double totalRating = 0;

        for (ReviewEntity review : bookReviews) {
            totalRating += review.getRating();
        }

        return totalRating / bookReviews.size();
    }

    public List<ReviewDTO> getBookReviews( Long bookId) {
        List<ReviewEntity> reviews= reviewRepository.findByBookId(bookId);
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        for (ReviewEntity review : reviews) {
            ReviewDTO reviewDTO=modelMapper.map(review,ReviewDTO.class);
            reviewDTOS.add(reviewDTO);
        }
        //reviews.stream().map(reviewEntity -> modelMapper.map(reviewEntity,ReviewDTO.class)).toList();
        return reviewDTOS;
    }



    public boolean deleteUserReview(Long reviewId, Long userId) {
        Optional<ReviewEntity> reviewOptional = reviewRepository.findById(reviewId);

        if (reviewOptional.isPresent()) {
            ReviewEntity review = reviewOptional.get();

            // Check if the review belongs to the specified user
            if (review.getUser().getUserId() == userId) {
                reviewRepository.delete(review);
                return true;
            } else {
                // User is not the owner of the review, handle the error as needed
                return false;
            }
        } else {
            // Review not found
            return false;
        }
    }


}

