package com.example.back.infraestructure.rest.auth;

import com.example.back.application.dto.LoginDto;
import com.example.back.application.dto.UserDto;
import com.example.back.application.mapper.UserMapper;
import com.example.back.application.service.AuthService;
import com.example.back.domain.entity.User;
import com.example.back.infraestructure.persistance.UserPersistanceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserPersistanceImpl userPersistance;
    private final UserMapper userMapper;

    public AuthController(AuthService authService, JwtService jwtService,
                          PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                          UserPersistanceImpl userPersistance, UserMapper userMapper) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userPersistance = userPersistance;
        this.userMapper = userMapper;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));
        UserDto user = authService.getUser(loginDto.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody UserDto userDto) {
        // En la base de datos no queremos guardar la contraseña, generamos
        // un hash.
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Optional<UserDto> userDtoRegistered = authService.register(userDto);
        if(!userDtoRegistered.isEmpty()){
            String token = jwtService.generateToken(userDtoRegistered.get());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping(value = "/registerUser")
    public ResponseEntity registerUser(@RequestBody UserDto userDto) {
        // En la base de datos no queremos guardar la contraseña, generamos
        // un hash.
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Optional<UserDto> userDtoRegistered = authService.registerUser(userDto);
        if(!userDtoRegistered.isEmpty()){
            String token = jwtService.generateToken(userDtoRegistered.get());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        return ResponseEntity.unprocessableEntity().build();
    }

    @PostMapping(value = "/valid")
    public Boolean isValid(@RequestBody String username, @RequestHeader("Authorization") String bearerToken){
        if( StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            Optional<User> user = this.userPersistance.find(username);
            UserDetails details = new AuthUserDetails(userMapper.toDto(user.get()));
            return this.jwtService.isTokenValid(token, details);
        }
        return false;
    }
}
