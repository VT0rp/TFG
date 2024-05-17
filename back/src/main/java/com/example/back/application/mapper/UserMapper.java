package com.example.back.application.mapper;

import com.example.back.application.dto.UserDto;
import com.example.back.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {

}
