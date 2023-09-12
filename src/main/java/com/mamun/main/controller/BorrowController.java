package com.mamun.main.controller;


import com.mamun.main.entity.BorrowingEntity;
import com.mamun.main.services.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/borrow")
public class BorrowController {
    private final BorrowService borrowService;

    @Autowired
    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/book/{bookId}/{userId}")
    public ResponseEntity<BorrowingEntity> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long userId) {
        BorrowingEntity borrowingEntity = borrowService.borrowBook(bookId, userId);
        return new ResponseEntity<>(borrowingEntity, HttpStatus.CREATED);
    }


    @PostMapping("/book/{bookId}/return/{userId}")
    public ResponseEntity<BorrowingEntity> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long userId) {
        BorrowingEntity borrowingEntity = borrowService.returnBook(bookId, userId);
        return new ResponseEntity<>(borrowingEntity, HttpStatus.OK);
    }

}

