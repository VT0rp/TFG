package com.example.back.application.service.impl;

import com.example.back.application.mapper.UserMapper;
import com.example.back.application.service.AuthService;
import com.example.back.application.dto.UserDto;
import com.example.back.domain.entity.User;
import com.example.back.domain.persistance.UserPersistance;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserPersistance userPersistence;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserPersistance userPersistence, UserMapper userMapper) {
        this.userPersistence = userPersistence;
        this.userMapper = userMapper;
    }

    public Optional<UserDto> register(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        Optional<User> registeredUser = userPersistence.save(user);
        if(!registeredUser.isEmpty()){
            return Optional.of(userMapper.toDto(registeredUser.get()));
        }
        return Optional.empty();
    }
    public Optional<UserDto> registerUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        Optional<User> registeredUser = userPersistence.saveUser(user);
        if(!registeredUser.isEmpty()){
            return Optional.of(userMapper.toDto(registeredUser.get()));
        }
        return Optional.empty();
    }

    @Override public Optional<UserDto> getUser(String username) {
        Optional<User> user = userPersistence.find(username);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(userMapper.toDto(user.get()));
    }
}
