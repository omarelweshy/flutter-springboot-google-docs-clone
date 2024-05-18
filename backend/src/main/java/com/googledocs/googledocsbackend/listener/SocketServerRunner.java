package com.googledocs.googledocsbackend.listener;

import com.googledocs.googledocsbackend.service.DocumentService;
import com.googledocs.googledocsbackend.DocData;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;


@Component
public class SocketServerRunner {

    private final SocketIOServer server;
    private final DocumentService documentService;

    @Autowired
    public SocketServerRunner(SocketIOServer server, DocumentService documentService) {
        this.server = server;
        this.documentService = documentService;
    }

    @PostConstruct
    private void startServer() {
        server.start();
        System.out.println("Socket.IO server started on port 9092");
    }

    @PreDestroy
    private void stopServer() {
        server.stop();
        System.out.println("Socket.IO server stopped");
    }

    @OnConnect
    public void onConnect(SocketIOClient client) {
        System.out.println("Client connected: " + client.getSessionId());
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("Client disconnected: " + client.getSessionId());
    }

    @OnEvent("join")
    public void onJoin(SocketIOClient client, String documentId) {
        System.out.println("Joined");
        client.joinRoom(documentId);
    }

    @OnEvent("typing")
    public void onTyping(SocketIOClient client, DocData data) {
        client.getNamespace().getRoomOperations(data.getRoom()).sendEvent("changes", data);
    }

    @OnEvent("save")
    public void onSave(SocketIOClient client, DocData data) {
        documentService.saveData(data);
    }

}

