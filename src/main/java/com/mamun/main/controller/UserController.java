package com.mamun.main.controller;


import com.mamun.main.model.UserDto;
import com.mamun.main.entity.BookEntity;
import com.mamun.main.entity.UserEntity;
import com.mamun.main.services.UserService;
import com.mamun.main.services2.UserService2;
import com.mamun.main.model.UserDto;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserService2 userService2;

    @Autowired
    public UserController(UserService userService, UserService2 userService2) {
        this.userService = userService;
        this.userService2 = userService2;
    }

    // Endpoint to add a new user
    @PostMapping("/add")
    public ResponseEntity<UserEntity> addUser(@RequestBody UserEntity userEntity) {
        UserEntity newUser = userService.addUser(userEntity);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long userId) {
        UserEntity user = userService.getUserById(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{userId}/books")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<BookEntity>> getUserBooks(@PathVariable Long userId) {
        List<BookEntity> books = userService.getUserBooks(userId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{userId}/books")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<BookEntity>> getAllBorrowedBooksByUserId(
            @PathVariable Long userId) {
        List<BookEntity> books = userService.getAllBorrowedBooksByUserId(userId);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> register (@RequestBody UserDto userDto) throws Exception {
        return new ResponseEntity<>(userService2.createUser(userDto), HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody UserDto userDto) {
//        // Authenticate user and generate a JWT token
//        Authentication authentication = userService.authenticate(userDto);
//        String token = jwtTokenProvider.generateToken(authentication);
//        return ResponseEntity.ok(token);
//    }


}

