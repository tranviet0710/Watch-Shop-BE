package com.example.WatchShop.service.impl;

import com.example.WatchShop.model.Roles;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.req.UsersReqDTO;
import com.example.WatchShop.repository.RolesRepository;
import com.example.WatchShop.repository.UsersRepository;
import com.example.WatchShop.service.i_service.JwtService;
import com.example.WatchShop.service.i_service.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
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
    private UsersRepository usersRepository;

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

    @Autowired
    private JwtService jwtService;

    @Override
    public Optional<Users> getUsersByEmailAndPassword(String email, String password) {
        Optional<Users> user = usersRepository.findByEmail(email);
        if (user.isPresent() && encoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public void addUsers(UsersReqDTO usersDTO) {
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
        usersRepository.save(users);
    }

    @Override
    public Users updateUsers(UsersReqDTO usersDTO, Long id) {
        Optional<Users> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isPresent()) {
            Users users = optionalUsers.get();
            if (usersDTO.getFullName() != null) {
                users.setFullName(usersDTO.getFullName());
            }
            if (usersDTO.getBirthDate() != null) {
                users.setBirthDate(usersDTO.getBirthDate());
            }
            if (usersDTO.getAddress() != null) {
                users.setAddress(usersDTO.getAddress());
            }
            if (usersDTO.getPassword() != null) {
                users.setPassword(encoder.encode(usersDTO.getPassword()));
            }

            if (usersDTO.getPhone() != null) {
                users.setPhone(usersDTO.getPhone());
            }
            usersRepository.save(users);

            return users;
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        usersRepository.deleteById(id);
    }

    @Override
    public Users save(Users users) {
        return usersRepository.save(users);
    }

    @Override
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    @Override
    public List<Users> findAllUser() {
        return usersRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
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

    public Optional<Users> getUserFromRequest(HttpServletRequest request) {
        String token = jwtService.getTokenFromRequest(request);
        String email = jwtService.extractEmail(token);
        Optional<Users> optionalUser = getUserByEmail(email);
        return optionalUser;
    }

    public boolean isCorrectPassword(Users user, String currentPassword) {
        return encoder.matches(currentPassword, user.getPassword());
    }
}