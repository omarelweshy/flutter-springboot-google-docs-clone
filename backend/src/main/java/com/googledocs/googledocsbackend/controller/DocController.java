package com.googledocs.googledocsbackend.controller;
import com.googledocs.googledocsbackend.model.User;
import com.googledocs.googledocsbackend.model.Doc;
import com.googledocs.googledocsbackend.repository.DocRepository;
import com.googledocs.googledocsbackend.repository.UserRepository;
import com.googledocs.googledocsbackend.utils.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/")
public class DocController {

    @Autowired
    private DocRepository docRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/doc/create")
    public ResponseEntity<Object> createDoc(@RequestBody Doc doc, @RequestHeader("x-auth-token") String token) {
        // Get user email from the token
        String userEmail = JwtUtil.getEmailFromToken(token);
        // Find the docs created by this user
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        // Save the new Document
        Doc newDoc = docRepository.save(doc);
        // Set Uid for the Document
        newDoc.setUid(reqUser.get().getId());
        // Save the new Document
        docRepository.save(newDoc);
        // Return the Document Object
        return new ResponseEntity<>(newDoc, HttpStatus.OK);
    }

    @GetMapping("/docs/me")
    public ResponseEntity<Object> createDoc(@RequestHeader("x-auth-token") String token) {
        // Get user email from the token
        String userEmail = JwtUtil.getEmailFromToken(token);
        // Find the docs created by this user
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        // check if exists?
        if (reqUser.isPresent()) { // Yes???
            // Return all the Documents
            List<Doc> docs = docRepository.findByUid(reqUser.get().getId());
            return new ResponseEntity<>(docs,HttpStatus.OK);
        } else { // No ???
            // return an empty list
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK);
        }
    }

    @PostMapping("/doc/title")
    public ResponseEntity<Object> updateDocTitle(@RequestBody Doc doc, @RequestHeader("x-auth-token") String token) {
        // Get email from the token
        String userEmail = JwtUtil.getEmailFromToken(token);
        // Get the user by email
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        // Find the Document by ID
        Optional<Doc> toUpdateDocOptional = docRepository.findById(doc.getId());
        // Check if the Document exists?
        if (toUpdateDocOptional.isPresent()) { // yes??
            Doc toUpdateDoc = toUpdateDocOptional.get();
            // Setting the new title 
            toUpdateDoc.setTitle(doc.getTitle());
            // Save 
            docRepository.save(toUpdateDoc);
            // return the Document Object
            return new ResponseEntity<>(toUpdateDoc, HttpStatus.OK);
        } else { // No
            // Return an error
            return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/doc/{id}")
    public ResponseEntity<Object> createDoc(@PathVariable String id, @RequestHeader("x-auth-token") String token) {
        // Get email from the token
        String userEmail = JwtUtil.getEmailFromToken(token);
        // Get the user by email !! we can ignore it
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        // Get the user by Doc
        Optional<Doc> doc = docRepository.findById(id);
        // Return it 
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }
}

