package com.mamun.main.services;


import com.mamun.main.entity.BookEntity;
import com.mamun.main.entity.BorrowingEntity;
import com.mamun.main.entity.UserEntity;
import com.mamun.main.exception.CustomException;
import com.mamun.main.repository.BorrowingRepository;
import com.mamun.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BorrowingRepository borrowingRepository;

    @Autowired
    public UserService(UserRepository userRepository, BorrowingRepository borrowingRepository) {
        this.userRepository = userRepository;
        this.borrowingRepository = borrowingRepository;
    }

    public UserEntity addUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    public UserEntity getUserById(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }

    public List<BookEntity> getAllBorrowedBooksByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("User not found"));

        List<BorrowingEntity> borrowingEntities = borrowingRepository.findByUser(user);

        List<BookEntity> books = borrowingEntities.stream()
                .map(BorrowingEntity::getBook)
                .collect(Collectors.toList());

        return books;
    }

}
