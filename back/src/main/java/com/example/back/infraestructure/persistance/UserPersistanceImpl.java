package com.example.back.infraestructure.persistance;

import com.example.back.application.dto.UserDto;
import com.example.back.domain.entity.Role;
import com.example.back.domain.entity.User;
import com.example.back.domain.persistance.UserPersistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserPersistanceImpl implements UserPersistance {

    private final com.example.back.infraestructure.persistance.UserMdbRepository userMdbRepository;

    public UserPersistanceImpl(com.example.back.infraestructure.persistance.UserMdbRepository userMdbRepository) {
        this.userMdbRepository = userMdbRepository;
    }

    @Override public Optional<User> save(User user) {
        Optional<User> userExist = userMdbRepository.findByUsername(user.getUsername());
        Optional<User> emailExist = userMdbRepository.findByEmail(user.getEmail());
        if(!userExist.isEmpty()){
            return Optional.empty();
        }
        if(!emailExist.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(userMdbRepository.save(user));
    }

    @Override public Optional<User> saveUser(User user) {
        Optional<User> userExist = userMdbRepository.findByUsername(user.getUsername());
        if(!userExist.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(userMdbRepository.save(user));
    }

    @Override public Optional<User> find(String username) {
        return userMdbRepository.findByUsername(username);
    }

    @Override public Optional<User> findById(String id){
        return userMdbRepository.findById(id);
    }

    @Override public Page<User> getPage(String email, Pageable pageable){
        return this.userMdbRepository.findAllByEmail(email, pageable);
    }

    @Override
    public Page<User> getPageFiltered(String email, String username, Pageable pageable){
        return this.userMdbRepository.findAllByEmailAndUsernameStartingWithIgnoreCase(username, email, pageable);
    }

    @Override
    public ResponseEntity delete(String id){
        Optional<User> user = userMdbRepository.findById(id);
        if(!user.isEmpty()){
            if(user.get().getRole() == Role.USER){
                userMdbRepository.deleteById(id);
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @Override
    public Optional<User> update(String id, UserDto userDto){
        Optional<User> user = userMdbRepository.findById(id);
        Optional<User> usernameExists = userMdbRepository.findByUsername(userDto.getUsername());
        if(user.isEmpty()){
            return Optional.empty();
        }
        if(usernameExists.isEmpty()){
            //Actualizar el usuario
            user.get().setUsername(userDto.getUsername());
            user.get().setFirstName(userDto.getFirstName());
            user.get().setLastName(userDto.getLastName());
            return Optional.of(userMdbRepository.save(user.get()));
        }
        if(!usernameExists.get().getId().equals(id)){
            return Optional.empty();
        }
        user.get().setUsername(userDto.getUsername());
        user.get().setFirstName(userDto.getFirstName());
        user.get().setLastName(userDto.getLastName());

        return Optional.of(userMdbRepository.save(user.get()));
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return this.userMdbRepository.findByUsername(username);
    }
}
