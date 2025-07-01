package com.example.myforum.service;

import java.util.List;

import com.example.myforum.model.Notification;
import com.example.myforum.model.User;

public interface NotificationService {
    void sendNotification(User sender, User receiver, String message);

    List<Notification> getUnreadNotifications(User receiver);

    List<Notification> getAllNotifications(User receiver);

    void markAsRead(Long notificationId);

}
