package com.example.myforum.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.myforum.enums.MessageType;
import com.example.myforum.model.GroupMessage;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        try {
            StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

            String username = (String) headerAccessor.getSessionAttributes().get("username");
            if (username != null) {
                logger.info("User Disconnected : " + username);

                GroupMessage chatMessage = new GroupMessage();
                chatMessage.setMessageType(MessageType.LEAVE);
                chatMessage.setSender(username);

                messagingTemplate.convertAndSend("/topic/public", chatMessage);
            }
        } catch (Exception ex) {
            logger.error("Error handling WebSocket disconnect event", ex);
            // Optionally, you could use ApiResponse here for further error handling or notification
        }
    }
}

