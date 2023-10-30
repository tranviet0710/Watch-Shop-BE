package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Roles;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.UsersDTO;
import com.example.WatchShop.repository.RolesRepository;
import com.example.WatchShop.repository.UsersReponsitory;
import com.example.WatchShop.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImlp implements UserService {

    @Autowired
    private UsersReponsitory usersReponsitory;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private Environment env;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;


    @Override
    public Optional<Users> getUsersByEmailAndPassword(String email, String password) {
        Optional<Users> user = usersReponsitory.findByEmail(email);
        if (user.isPresent() && encoder.matches(password, user.get().getPassword())) {
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
        users.setRoles(roles);
        usersReponsitory.save(users);
    }

    @Override
    public Users updateUsers(UsersDTO usersDTO, Long id) {
        Optional<Users> optionalUsers = usersReponsitory.findById(id);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            users.setFullName(usersDTO.getFullName());
            users.setBirthDate(usersDTO.getBirthDate());
            users.setAddress(usersDTO.getAddress());
            users.setPassword(encoder.encode(usersDTO.getPassword()));

            // update email if does not exists
            if (!usersReponsitory.existsByEmail(usersDTO.getEmail())) {
                users.setEmail(usersDTO.getEmail());
            }

            users.setPhone(usersDTO.getPhone());
            usersReponsitory.save(users);

            return users;
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        usersReponsitory.deleteById(id);
    }

    @Override
    public Users save(Users users) {
        return usersReponsitory.save(users);
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return usersReponsitory.findByEmail(email);
    }

    @Override
    public List<Users> findAllUser() {
        return usersReponsitory.findAll();
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersReponsitory.existsByEmail(email);
    }

    // Send email
    public void sendEmail(Users user, String subject, String emailContent) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(user.getEmail());
        helper.setSubject(subject);
        helper.setText(emailContent, true);
        javaMailSender.send(message);
    }

    @Override
    public void sendRecoverPassword(Users user, String password) throws MessagingException {
        Context context = new Context();
        context.setVariable("username", user.getFullName());
        context.setVariable("password", password);
        String emailContent = templateEngine.process("recovery_password", context);
        sendEmail(user, "Forgot password", emailContent);

    }






}