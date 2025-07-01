package com.example.myforum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.example.myforum.dto.ApiResponse;
import com.example.myforum.model.GroupMessage;
import com.example.myforum.service.GroupMessageService;

public class GroupChatController {
    @Autowired
    private GroupMessageService groupMessageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/group.sendMessage")
    public void sendMessage(@Payload GroupMessage message) {
        try {
            groupMessageService.saveMessage(message);
            messagingTemplate.convertAndSend("/topic/group." + message.getGroupId(), message);
            messagingTemplate.convertAndSendToUser(
                message.getSender(), "/queue/group.status",
                new ApiResponse(true, "Message sent successfully", 200)
            );
        } catch (Exception e) {
            // Log the exception (consider using a logger in production)
            System.err.println("Error sending group message: " + e.getMessage());
            messagingTemplate.convertAndSendToUser(
                message.getSender(), "/queue/group.status",
                new ApiResponse(false, "Error: " + e.getMessage(), 500)
            );
        }
    }
}
