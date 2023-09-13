package com.mamun.main.repository;


import com.mamun.main.entity.UserEntity;
import com.mamun.main.model.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
//    UserEntity findByUserId(Integer userId);

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserId(String userId);
    Optional<UserEntity> findByUserId(Long userId);



    UserDto createUser(UserDto user) throws Exception;

}