package com.googledocs.googledocsbackend.controller;
import com.googledocs.googledocsbackend.model.User;
import com.googledocs.googledocsbackend.repository.UserRepository;
import com.googledocs.googledocsbackend.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.*;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/signup")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            String token = JwtUtil.generateToken(user.getEmail());
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("id", existingUser.get().getId());
            data.put("name", existingUser.get().getName());
            data.put("profilePic", existingUser.get().getProfilePic());
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            User savedUser = userRepository.save(user);
            String token = JwtUtil.generateToken(user.getEmail());
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("id", savedUser.getId());
            data.put("name", savedUser.getName());
            data.put("profilePic", savedUser.getProfilePic());
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUserById(@RequestHeader("x-auth-token") String token) {
        String userEmail = JwtUtil.getEmailFromToken(token);
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        Map<String, Object> data = new HashMap<>();
        data.put("user", reqUser.get());
        data.put("token", token);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}

