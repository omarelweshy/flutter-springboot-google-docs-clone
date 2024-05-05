package com.googledocs.googledocsbackend.controller;
import com.googledocs.googledocsbackend.model.User;
import com.googledocs.googledocsbackend.repository.UserRepository;
import com.googledocs.googledocsbackend.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            // User with the same email already exists, return it
            return new ResponseEntity<>(existingUser.get(), HttpStatus.OK);
        } else {
            // User with the email does not exist, create a new user
            User savedUser = userRepository.save(user);
            String token = JwtUtil.generateToken(user.getEmail());

            return ResponseEntity.status(HttpStatus.CREATED).body(token);// return new ResponseEntity<>({id: "1"}, HttpStatus.CREATED);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: "));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    // Define a TokenResponse class to represent the token in the response
    private static class TokenResponse {
        private final String token;

        public TokenResponse(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }
}

