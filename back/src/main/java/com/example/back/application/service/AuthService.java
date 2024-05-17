package com.example.back.application.service;

import com.example.back.application.dto.UserDto;

import java.util.Optional;

public interface AuthService {
    Optional<UserDto> register(UserDto userDto);
    Optional<UserDto> registerUser(UserDto userDto);
    Optional<UserDto> getUser(String username);
}
