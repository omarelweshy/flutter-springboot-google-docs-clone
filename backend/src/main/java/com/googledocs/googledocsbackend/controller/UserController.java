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
        // Check for the user if he is exists ?
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        // exists ???????
        if (existingUser.isPresent()) { // YES
            // generate token for him
            String token = JwtUtil.generateToken(user.getEmail());
            // Create the objet you will return to the user
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("id", existingUser.get().getId());
            data.put("name", existingUser.get().getName());
            data.put("profilePic", existingUser.get().getProfilePic());
            // return it
            return new ResponseEntity<>(data, HttpStatus.OK);
        } else {
            // he is a new user, so?
            // Save him in the database
            User savedUser = userRepository.save(user);
            // generate token for him
            String token = JwtUtil.generateToken(user.getEmail());
            // Create the objet you will return to the user
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            data.put("id", savedUser.getId());
            data.put("name", savedUser.getName());
            data.put("profilePic", savedUser.getProfilePic());
            // return it
            return new ResponseEntity<>(data, HttpStatus.OK);
        }
    }

    @GetMapping("/")
    public ResponseEntity<Object> getUserById(@RequestHeader("x-auth-token") String token) {
        // Get the Email from the token 
        String userEmail = JwtUtil.getEmailFromToken(token);
        // Search in the database with the founded Email
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        // Build the object you will return to the endpoint
        Map<String, Object> data = new HashMap<>();
        data.put("user", reqUser.get());
        data.put("token", token);
        // Return it
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}

