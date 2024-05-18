package com.googledocs.googledocsbackend.service;

import com.googledocs.googledocsbackend.model.Doc;
import com.googledocs.googledocsbackend.repository.DocRepository;
import com.googledocs.googledocsbackend.DocData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

    private final DocRepository documentRepository;

    @Autowired
    public DocumentService(DocRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void saveData(DocData data) {
        Doc document = documentRepository.findById(data.getRoom()).orElse(new Doc());
        document.setContent(data.getDelta());
        documentRepository.save(document);
    }
}

