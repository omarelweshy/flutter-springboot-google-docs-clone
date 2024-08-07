package com.googledocs.googledocsbackend.repository;

import com.googledocs.googledocsbackend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    // Function to be use by the controllers
    Optional<User> findByEmail(String email);
}

