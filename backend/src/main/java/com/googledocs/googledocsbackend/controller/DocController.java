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
        String userEmail = JwtUtil.getEmailFromToken(token);
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        Doc newDoc = docRepository.save(doc);
        newDoc.setUid(reqUser.get().getId());
        docRepository.save(newDoc);
        return new ResponseEntity<>(newDoc, HttpStatus.OK);
    }

    @GetMapping("/docs/me")
    public ResponseEntity<Object> createDoc(@RequestHeader("x-auth-token") String token) {
        String userEmail = JwtUtil.getEmailFromToken(token);
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        if (reqUser.isPresent()) {
            List<Doc> docs = docRepository.findByUid(reqUser.get().getId());
            return new ResponseEntity<>(docs,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.OK);
        }
    }

    @PostMapping("/doc/title")
    public ResponseEntity<Object> updateDocTitle(@RequestBody Doc doc, @RequestHeader("x-auth-token") String token) {
        String userEmail = JwtUtil.getEmailFromToken(token);
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        Optional<Doc> toUpdateDocOptional = docRepository.findById(doc.getId());
        if (toUpdateDocOptional.isPresent()) {
            Doc toUpdateDoc = toUpdateDocOptional.get();
            toUpdateDoc.setTitle(doc.getTitle());
            docRepository.save(toUpdateDoc);
            return new ResponseEntity<>(toUpdateDoc, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Document not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/doc/{id}")
    public ResponseEntity<Object> createDoc(@PathVariable String id, @RequestHeader("x-auth-token") String token) {
        String userEmail = JwtUtil.getEmailFromToken(token);
        Optional<User> reqUser = userRepository.findByEmail(userEmail);
        Optional<Doc> doc = docRepository.findById(id);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }
}

