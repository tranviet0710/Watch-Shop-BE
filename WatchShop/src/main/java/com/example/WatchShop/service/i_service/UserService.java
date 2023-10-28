package com.example.WatchShop.service.i_service;

import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.req.UsersReqDTO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<Users> getUsersByEmailAndPassword(String email, String password);

    Optional<Users> getUserByEmail(String email);

    Optional<Users> getUserById(Long id);

    List<Users> findAllUser();

    void addUsers(UsersReqDTO usersDTO);

    Users updateUsers(UsersReqDTO usersDTO, Long id);

    void deleteById(Long id);

    Users save(Users users);

    boolean existsByEmail(String email);

    void sendRecoverPassword(Users user, String password) throws MessagingException;

    public Optional<Users> getUserFromRequest(HttpServletRequest request);

}
