package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Roles;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.UsersDTO;
import com.example.WatchShop.repository.RolesRepository;
import com.example.WatchShop.repository.UsersReponsitory;
import com.example.WatchShop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImlp implements UserService {

    @Autowired
    private UsersReponsitory usersReponsitory;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Users getUsers(String email, String password) {
        Users user = usersReponsitory.findUserByEmail(email);
        if (user != null && encoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }


    @Override
    public void addUsers(UsersDTO usersDTO) {
        Users users = new Users();
        users.setFullName(usersDTO.getFullName());
        users.setBirthDate(usersDTO.getBirthDate());
        users.setAddress(usersDTO.getAddress());
        // encrypt the password hash
        users.setPassword(encoder.encode(usersDTO.getPassword()));
        users.setEmail(usersDTO.getEmail());
        users.setPhone(usersDTO.getPhone());

        // Create Role_User
        Roles roles = rolesRepository.findByName("ROLE_USER");
        users.setIdRole(roles);

        usersReponsitory.save(users);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersReponsitory.existsByEmail(email);
    }
}
