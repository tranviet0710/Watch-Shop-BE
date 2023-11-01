package com.example.WatchShop.controller.user;

import com.example.WatchShop.model.Users;
import com.example.WatchShop.model.dto.req.UsersReqDTO;
import com.example.WatchShop.model.dto.res.UserResDTO;
import com.example.WatchShop.service.i_service.JwtService;
import com.example.WatchShop.service.i_service.UserService;
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
@RequestMapping("api")
public class UsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UsersReqDTO usersDTO) {
        if (userService.existsByEmail(usersDTO.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("status", "fail", "message", "Email already existed!"));
        }
        userService.addUsers(usersDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "message", "User register successfully."));
    }

    @CrossOrigin(origins = "http://localhost:3000/")
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UsersReqDTO usersDTO) {
        Optional<Users> user = userService.getUsersByEmailAndPassword(usersDTO.getEmail(), usersDTO.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("status", "fail", "message", "Invalid email or password!"));
        }
        // Generate access token
        String accessToken = jwtService.generateToken(user.get(), user.get().getAuthorities());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", new UserResDTO(user.get()), "accessToken", accessToken));
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

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(HttpServletRequest request, @RequestBody Map<String, String> passwords) {
        Users user = userService.getUserFromRequest(request).get();
        if (userService.isCorrectPassword(user, passwords.get("currentPassword"))) {
            String newPassword = passwordEncoder.encode(passwords.get("newPassword"));
            user.setPassword(newPassword);
            userService.save(user);
            return ResponseEntity.ok().body("Change password successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Your current password is not correct. Please try again!");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUser() {
        List<Users> usersList = userService.findAllUser();
        List<UserResDTO> userResDTOS = usersList.stream().map(UserResDTO::new).toList();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", userResDTOS));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserDetail(@PathVariable("id") Long id) {
        Users user = userService.getUserById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "data", new UserResDTO(user)));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UsersReqDTO usersDTO, @PathVariable("id") Long id) {
        Users updateUsers = userService.updateUsers(usersDTO, id);
        if (updateUsers == null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "fail", "message", "Users update failed"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "message", "Users update successfully"));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("status", "success", "message", "Users delete successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", "fail", "message", "Users delete failed"));
        }
    }


}