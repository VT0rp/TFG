package com.example.back.infraestructure.rest;

import com.example.back.application.dto.UserDto;
import com.example.back.application.service.UserService;
import com.example.back.domain.entity.Role;
import com.example.back.domain.entity.User;
import com.example.back.infraestructure.rest.auth.AuthResponse;
import com.example.back.infraestructure.rest.auth.JwtService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;

    public UserController(JwtService jwtService, UserService userService){
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("/username")
    public Map<String, String> getUserName(@RequestHeader("Authorization") String bearerToken){
        if( StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            String username = jwtService.getUsernameFromToken(token);
            Map<String, String> response = new HashMap<>();
            response.put("username", username);
            return response;
        }
        return null;
    }

    @GetMapping("/role")
    public Map<String, Role> getRole(@RequestHeader("Authorization") String bearerToken){
        if( StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            String username = jwtService.getUsernameFromToken(token);
            Optional<UserDto> user = userService.getUser(username);
            if(!user.isEmpty()){
                Map<String, Role> response = new HashMap<>();
                response.put("role", user.get().getRole());
                return response;
            }
            return null;
        }
        return null;
    }

    @GetMapping("/getUser")
    public UserDto getInfoUser(@RequestHeader("Authorization") String bearerToken){
        if( StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            String username = jwtService.getUsernameFromToken(token);
            Optional<UserDto> user = userService.getUser(username);
            if(!user.isEmpty()){
                return user.get();
            }
            return null;
        }
        return null;
    }

    @GetMapping("/getUserById/{id}")
    public UserDto getInfoUserById(@PathVariable("id") String id){
        Optional<UserDto> user = userService.getUserById(id);
        if(!user.isEmpty()){
            return user.get();
        }
        return null;
    }

    @GetMapping("/page")
    public Page<User> usersPaginated(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("email") String email){
        Pageable pageable = PageRequest.of(page, size);
       return userService.getPage(email, pageable);
    }

    @GetMapping("/filtered")
    public Page<User> usersFiltered
            (@RequestParam("page") int page, @RequestParam("size") int size,
             @RequestParam("email") String email, @RequestParam("username") String username)
    {
       Pageable pageable = PageRequest.of(page, size);
       return userService.getPageFiltered(username, email, pageable);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") String id){
        return userService.deleteUser(id);
    }

    @PutMapping("/update/{id}/{updaterId}")
    public ResponseEntity updateUser(@PathVariable("id") String id, @PathVariable("updaterId") String updaterId, @RequestBody UserDto userDto){
        Optional<UserDto> userUpdated = userService.updateUser(id, updaterId, userDto);
        if(userUpdated.isEmpty()){
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        String token = jwtService.generateToken(userUpdated.get());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/getUserByUsername/{nombre}")
    public Optional<UserDto> getUserByUsername(@PathVariable("nombre")String nombre){
        return this.userService.getUserByUsername(nombre);
    }
}
