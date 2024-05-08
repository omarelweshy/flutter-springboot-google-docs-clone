package com.googledocs.googledocsbackend.repository;

import com.googledocs.googledocsbackend.model.Doc;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocRepository extends MongoRepository<Doc, String> {
    List<Doc> findByUid(String uid);
    Doc findById(Long id);
}

