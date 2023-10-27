package com.example.WatchShop.service;

import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.UsersDTO;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<Users> getUsersByEmailAndPassword(String email, String password);

    Optional<Users> getUserByEmail(String email);

    Optional<Users> getUserById(Long id);

    List<Users> findAllUser();

    void addUsers(UsersDTO usersDTO);

    Users updateUsers(UsersDTO usersDTO, Long id);

    void deleteById(Long id);

    Users save(Users users);

    boolean existsByEmail(String email);

    void sendRecoverPassword(Users user, String password) throws MessagingException;

}
