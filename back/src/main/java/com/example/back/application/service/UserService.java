package com.example.back.application.service;

import com.example.back.application.dto.UserDto;
import com.example.back.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> getUser(String username);

    Optional<UserDto> getUserById(String id);

    Page<User> getPage(String email, Pageable pageable);

    Page<User> getPageFiltered(String username, String email, Pageable pageable);

    ResponseEntity deleteUser(String id);

    Optional<UserDto> updateUser(String id, UserDto userDto);

}
