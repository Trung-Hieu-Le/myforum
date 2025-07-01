package com.example.myforum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.myforum.model.Notification;
import com.example.myforum.service.NotificationService;

@Controller
public class NotificationWebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/notify")
    public void notify(@Payload Notification notification) {
        notificationService.sendNotification(
            notification.getSender(),
            notification.getReceiver(),
            notification.getContent()
        );
        messagingTemplate.convertAndSendToUser(
            notification.getReceiver().getUsername(),
            "/queue/notifications",
            notification
        );
    }
}
