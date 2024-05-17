package com.example.back.application.service.impl;

import com.example.back.application.dto.UserDto;
import com.example.back.application.mapper.UserMapper;
import com.example.back.application.service.UserService;
import com.example.back.domain.entity.User;
import com.example.back.domain.persistance.UserPersistance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserPersistance userPersistance;
    private final UserMapper userMapper;

    public UserServiceImpl(UserPersistance userPersistance, UserMapper userMapper){
        this.userPersistance = userPersistance;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<UserDto> getUser(String username){
        Optional<User> user = userPersistance.find(username);
        if(user.isPresent()){
            return Optional.of(userMapper.toDto(user.get()));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserDto> getUserById(String id){
        Optional<User> user = userPersistance.findById(id);
        if(user.isPresent()){
            return Optional.of(userMapper.toDto(user.get()));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public Page<User> getPage(String email, Pageable pageable){
        return userPersistance.getPage(email, pageable);
    }

    @Override
    public Page<User> getPageFiltered(String username, String email, Pageable pageable){
        return userPersistance.getPageFiltered(username, email, pageable);
    }

    @Override
    public ResponseEntity deleteUser(String id){
        return userPersistance.delete(id);
    }

    @Override
    public Optional<UserDto> updateUser(String id, UserDto userDto){
        Optional<User> userUpdated = userPersistance.update(id, userDto);
        if(userUpdated.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(userMapper.toDto(userUpdated.get()));
    }
}
