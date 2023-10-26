package com.example.WatchShop.controller.user;

import com.example.WatchShop.model.Brands;
import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.UsersDTO;
import com.example.WatchShop.service.UserService;
import com.example.WatchShop.util.JwtTokenGenerator;
import com.example.WatchShop.util.PasswordUtils;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class UsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsersDTO usersDTO) {
        if (userService.existsByEmail(usersDTO.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "Email already exists in DB"));
        }
        userService.addUsers(usersDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "message", "User register successsfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsersDTO usersDTO) {
        Optional<Users> user = userService.getUsersByEmailAndPassword(usersDTO.getEmail(), usersDTO.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "fail", "message", "Invalid email or password"));
        }

        // Generate access token
        String accessToken = JwtTokenGenerator.generateAccessToken(user);

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", user, "accessToken", accessToken));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@RequestBody Map<String, String> email) throws MessagingException {
        Optional<Users> user = userService.getUserByEmail(email.get("email"));
        if (user.isPresent()) {
            String password = PasswordUtils.generateRandomPassword(12);
            user.get().setPassword(passwordEncoder.encode(password));
            userService.save(user.get());
            userService.sendRecoverPassword(user.get(), password);
            return ResponseEntity.ok().body("Please check your email to get recovery password!");
        } else {
            return ResponseEntity.ok().body("Invalid email or email has not been registered!");
        }
    }

    @GetMapping("/list-users")
    public ResponseEntity<?> getAllUser(){
        List<Users> usersList = userService.findAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", usersList));
    }

    @PutMapping("/update-users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UsersDTO usersDTO, @PathVariable("id") Long id)  {
      Users updateUsers = userService.updateUsers(usersDTO, id);
        if(updateUsers == null){
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "fail", "message", "Users update failed"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "message", "Users update successsfully"));
    }

    @DeleteMapping("/delete-users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id)  {
        try {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "message", "Users delete successsfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "fail", "message", "Users delete failed"));
        }
    }



}