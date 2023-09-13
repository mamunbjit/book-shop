package com.mamun.main.services2;


import com.mamun.main.entity.UserEntity;
import com.mamun.main.model.UserDto;

import java.util.Optional;

public interface UserService2 {
    UserDto createUser(UserDto user) throws Exception;
    UserDto getUser(String email);

    Optional<UserEntity> findByUserId(Long userId);

    UserDto getUserByUserId(Long id) throws Exception;

    UserDto getUserByUserId(String id) throws Exception;


}
