package com.example.back.infraestructure.rest;

import com.example.back.application.dto.UserDto;
import com.example.back.application.service.UserService;
import com.example.back.domain.entity.Role;
import com.example.back.domain.entity.User;
import com.example.back.infraestructure.rest.auth.JwtService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    private final JwtService jwtService;
    private final UserService userService;

    public DemoController(JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping(value = "/hello")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from secure endpoint");
        return response;
    }

}