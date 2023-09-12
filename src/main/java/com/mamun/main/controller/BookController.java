package com.mamun.main.controller;

import com.mamun.main.entity.BookEntity;
import com.mamun.main.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("books")
public class BookController {
    public final BookService bookService;

    @Autowired
    public BookController(BookService bookService){
        this.bookService=bookService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookEntity> addBook(@RequestBody BookEntity bookEntity){
        BookEntity newBook= bookService.addBook(bookEntity);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long bookId) {
        Optional<BookEntity> bookOptional = bookService.getBookById(bookId);
        if (bookOptional.isPresent()) {
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookEntity>> getAllBooks() {
        List<BookEntity> books = bookService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @DeleteMapping("/delete/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        boolean deleted = bookService.deleteBook(bookId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/update/{bookId}")
    public ResponseEntity<BookEntity> updateBook(
            @PathVariable Long bookId,
            @RequestBody BookEntity updatedBook) {
        BookEntity updated = bookService.updateBook(bookId, updatedBook);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
