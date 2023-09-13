package com.mamun.main.services;


import com.mamun.main.entity.UserEntity;
import com.mamun.main.model.UserDto;
import com.mamun.main.repository.UserRepository;
import com.mamun.main.services.UserService;
import com.mamun.main.services2.UserService2;
import com.mamun.main.utils.JWTUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


//public class UserServiceImpl implements UserRepository, UserDetailsService {}
@Service
@Transactional
public class UserServiceImpl implements UserService2, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new Exception("Record already exists");
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        Long publicUserId = JWTUtils.generateUserID(10); // Make sure JWTUtils.generateUserID returns a Long
        userEntity.setUserId(publicUserId);
        UserEntity storedUserDetails =userRepository.save(userEntity);
        UserDto returnedValue = modelMapper.map(storedUserDetails,UserDto.class);
        return returnedValue;
    }

    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        if(userEntity == null) throw new UsernameNotFoundException("No record found");
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);
        return returnValue;
    }


    @Override
    public UserDto getUserByUserId(String userId) throws Exception {
        UserDto returnValue = new UserDto();

        try {
            Long longUserId = Long.parseLong(userId);
            UserEntity userEntity = userRepository.findByUserId(longUserId).orElseThrow(Exception::new);
            BeanUtils.copyProperties(userEntity, returnValue);
        } catch (NumberFormatException e) {
            // Handle the case where userId is not a valid Long
            throw new Exception("Invalid user ID");
        }

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email).get();
        if(userEntity==null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(),userEntity.getPassword(),
                true,true,true,true,new ArrayList<>());
    }
}
