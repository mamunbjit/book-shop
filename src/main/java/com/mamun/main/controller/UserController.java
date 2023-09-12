package com.mamun.main.controller;



import com.mamun.main.entity.BookEntity;
import com.mamun.main.entity.UserEntity;
import com.mamun.main.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint to add a new user
    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity userEntity) {
        UserEntity newUser = userService.addUser(userEntity);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long userId) {
        UserEntity user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{userId}/books")
    public ResponseEntity<List<BookEntity>> getAllBorrowedBooksByUserId(
            @PathVariable Long userId) {
        List<BookEntity> books = userService.getAllBorrowedBooksByUserId(userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

}

