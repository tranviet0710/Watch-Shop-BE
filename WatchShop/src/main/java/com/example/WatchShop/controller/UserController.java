package com.example.WatchShop.controller;

import com.example.WatchShop.model.Users;
import com.example.WatchShop.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class UserController
{
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users")
    public List<Users> getAllUsers(){
        return userRepo.findAll();
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
