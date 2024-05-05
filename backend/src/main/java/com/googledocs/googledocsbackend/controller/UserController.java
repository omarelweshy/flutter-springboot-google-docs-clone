package com.googledocs.googledocsbackend.controller;
import com.googledocs.googledocsbackend.model.User;
import com.googledocs.googledocsbackend.repository.UserRepository;
import com.googledocs.googledocsbackend.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static class TokenResponse {
        private final String id;
        private final String token;

        public TokenResponse(String id, String token) {
            this.id = id;
            this.token = token;
        }
    }

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
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("id", savedUser.getId());
            return new ResponseEntity<>(data, HttpStatus.OK);

            // return ResponseEntity.status(HttpStatus.CREATED)
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: "));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}

