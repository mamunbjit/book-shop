package com.mamun.main.services;


import com.mamun.main.entity.BookEntity;
import com.mamun.main.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity addBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<BookEntity> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }



    public boolean deleteBook(Long bookId) {
        Optional<BookEntity> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            bookRepository.deleteById(bookId);
            return true;
        } else {
            return false;
        }
    }


    public BookEntity updateBook(Long bookId, BookEntity updatedBook) {
        Optional<BookEntity> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            BookEntity existingBook = bookOptional.get();

            // Update fields as needed
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setDescription(updatedBook.getDescription());
            existingBook.setAvailability(updatedBook.getAvailability());

            // Save the updated book
            return bookRepository.save(existingBook);
        } else {
            return null;
        }
    }

}
