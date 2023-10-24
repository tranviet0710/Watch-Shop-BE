package com.example.WatchShop.service;

import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.UsersDTO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface UserService {

    Optional<Users> getUsersByEmailAndPassword(String email, String password);

    Optional<Users> getUserByEmail(String email);

    void addUsers(UsersDTO usersDTO);

    Users updateUsers(UsersDTO usersDTO, Long id);

    void deleteById(Long id);

    Users save(Users users);

    boolean existsByEmail(String email);

    void sendRecoverPassword(Users user, String password) throws MessagingException;




}
