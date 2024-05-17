package com.example.back.infraestructure.persistance;

import com.example.back.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserMdbRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    Page<User> findAllByEmailAndUsernameContainingIgnoreCase(String username, String email, Pageable pageable);

    Page<User> findAllByEmailAndUsernameStartingWithIgnoreCase(String emailPattern, String usernamePattern, Pageable pageable);

    Page<User> findAllByEmail(String email, Pageable pageable);

    Boolean existsByUsername(String username);

    Optional<User> findByUsernameAndEmail(String username, String email);

}
