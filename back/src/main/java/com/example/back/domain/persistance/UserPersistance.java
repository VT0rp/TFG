package com.example.back.domain.persistance;

import com.example.back.application.dto.UserDto;
import com.example.back.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserPersistance {
    Optional<User> save(User user);
    Optional<User> saveUser(User user);
    Optional<User> find(String username);

    Optional<User> findById(String id);

    Page<User> getPage(String email, Pageable pageable);

    Page<User> getPageFiltered(String username, String email, Pageable pageable);

    ResponseEntity delete(String id);

    Optional<User> update(String id, UserDto userDto);

    Optional<User> getUserByUsername(String username);
}
