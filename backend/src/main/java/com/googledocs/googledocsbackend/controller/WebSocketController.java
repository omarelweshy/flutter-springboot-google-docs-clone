// package com.googledocs.googledocsbackend.controller;
//
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;
// import com.googledocs.googledocsbackend.model.Doc;
// import com.googledocs.googledocsbackend.repository.DocRepository;
// import com.googledocs.googledocsbackend.MessageData;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
//
//
// @Controller
// public class WebSocketController {
//     private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);
//
//     @Autowired
//     private DocRepository documentRepository;
//
//     @MessageMapping("/join")
//     public void joinRoom(String documentId) {
//         logger.info("Attempting to save data: {}", documentId);
//         // Logic to handle room joining if needed
//     }
//
//     @MessageMapping("/typing")
//     @SendTo("/topic/changes")
//     public Object handleTyping(MessageData data) {
//         // Broadcast changes to subscribers of a topic
//         return data; // Replace Object with your specific data class
//     }
//
//     @MessageMapping("/save")
//     public void saveData(MessageData data) {
//         logger.info("Attempting to save data: {}", data);
//         try {
//             Doc document = documentRepository.findById(data.getRoom()).orElseGet(() -> {
//                 logger.info("No document found with ID: {}. Creating new document.", data.getRoom());
//                 return new Doc();
//             });
//             document.setContent(data.getDelta());
//             document = documentRepository.save(document);
//             logger.info("Document saved successfully with ID: {}", document.getId());
//         } catch (Exception e) {
//             logger.error("Error saving document: ", e);
//         }
//     }
//
//
//     // @MessageMapping("/save")
//     // public void saveData(MessageData data) {
//     //     Doc document = documentRepository.findById(data.getRoom()).orElse(new Doc());
//     //     document.setContent(data.getDelta());
//     //     documentRepository.save(document);
//     // }
// }
