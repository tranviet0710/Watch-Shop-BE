package com.example.WatchShop.controller;

import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.UsersDTO;
import com.example.WatchShop.service.UserService;
import com.example.WatchShop.util.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin
public class UsersController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsersDTO usersDTO) {
        if (userService.existsByEmail(usersDTO.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("status", 400, "error", "Email already exists in DB"));
        }
        userService.addUsers(usersDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", 200, "success", "User register successsfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsersDTO usersDTO) {
        Users user = userService.getUsers(usersDTO.getEmail(), usersDTO.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", 401, "error", "Invalid email or password"));
        }

        // Generate access token
        String accessToken = JwtTokenGenerator.generateAccessToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", 200, "data", user, "accessToken", accessToken));
    }

}