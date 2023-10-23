package com.example.WatchShop.service;

import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.UsersDTO;

public interface UserService {

    Users getUsers(String email, String password);

    void addUsers(UsersDTO usersDTO);

    boolean existsByEmail(String email);


}
