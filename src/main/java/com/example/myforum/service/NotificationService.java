package com.example.myforum.service;

import java.util.List;

import com.example.myforum.model.Notification;

public interface NotificationService {
    void sendNotification(String recipient, String message);

    List<Notification> getUnreadNotifications(String recipient);

    void markAsRead(Long notificationId);
}
