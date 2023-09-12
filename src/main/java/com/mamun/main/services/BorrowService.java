package com.mamun.main.services;


import com.mamun.main.entity.BookEntity;
import com.mamun.main.entity.BorrowingEntity;
import com.mamun.main.entity.UserEntity;
import com.mamun.main.exception.CustomException;
import com.mamun.main.repository.BookRepository;
import com.mamun.main.repository.BorrowingRepository;
import com.mamun.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class BorrowService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowingRepository borrowingRepository;

    @Autowired
    public BorrowService(
            BookRepository bookRepository,
            UserRepository userRepository,
            BorrowingRepository borrowingRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowingRepository = borrowingRepository;
    }


    public BorrowingEntity borrowBook(Long bookId, Long userId) {
        try {
            // Check if the book exists and is available
            BookEntity book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new CustomException("Book not found"));

            if (!book.getAvailability()) {
                throw new CustomException("Book is not available for borrowing");
            }

            // Check if the user exists
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new CustomException("User not found"));

            // Calculate due date (e.g., 30 days from today)
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE, -15);
            Date borrowedDate = calendar.getTime();

            calendar.setTime(currentDate);
            calendar.add(Calendar.DATE, 10);
            Date returnDate = calendar.getTime();

            book.setAvailability(false);
            bookRepository.save(book);

            // Create a borrowing record
            BorrowingEntity borrowingEntity = new BorrowingEntity();
            borrowingEntity.setUser(user);
            borrowingEntity.setBook(book);
            borrowingEntity.setBorrowDate(borrowedDate);
            borrowingEntity.setReturnDate(returnDate);
            borrowingRepository.save(borrowingEntity);

            return borrowingEntity;
        } catch (CustomException ex) {
            throw new CustomException(ex.getMessage());
        }
    }


    public BorrowingEntity returnBook(Long bookId, Long userId) {
        try {
            Optional<BorrowingEntity> optionalBorrowingEntity = borrowingRepository.findByBookId(bookId);

            if (optionalBorrowingEntity.isEmpty()) {
                throw new CustomException("Borrowing record not found for the specified book");
            }

            BorrowingEntity borrowingEntity = optionalBorrowingEntity.get();

            if (userId == borrowingEntity.getUser().getUserId()) {
                BookEntity book = borrowingEntity.getBook();
                book.setAvailability(true);
                bookRepository.save(book);

                Date currentDate = new Date();
                Date returnDate = new Date(currentDate.getTime() - 5 * 24 * 60 * 60 * 1000);
                borrowingEntity.setReturnDate(returnDate);

                borrowingRepository.save(borrowingEntity);

                return borrowingEntity; // Book successfully returned
            } else {
                throw new CustomException("Invalid user for returning the book");
            }

        } catch (CustomException ex) {
            throw ex; // Re-throw the custom exception
        }
    }
}

