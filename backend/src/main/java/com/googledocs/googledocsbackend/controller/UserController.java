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
            data.put("id", user.getId());
            data.put("name", user.getName());
            data.put("profilePic", user.getProfilePic());
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
        Boolean auth = JwtUtil.validateToken(token);
        System.out.println(auth);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

