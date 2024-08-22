package com.example.stage.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Code pour gérer l'établissement de la connexion
        System.out.println("New WebSocket connection established");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Code pour gérer les messages reçus
        System.out.println("Received message: " + message.getPayload());
        // Vous pouvez envoyer une réponse au client
        session.sendMessage(new TextMessage("Message received"));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Code pour gérer la fermeture de la connexion
        System.out.println("WebSocket connection closed");
    }
}
