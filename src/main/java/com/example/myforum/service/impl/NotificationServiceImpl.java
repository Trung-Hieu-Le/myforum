package com.example.myforum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myforum.model.Notification;
import com.example.myforum.model.User;
import com.example.myforum.repository.NotificationRepository;
import com.example.myforum.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void sendNotification(User sender, User receiver, String message) {
        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setContent(message);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUnreadNotifications(User receiver) {
        return notificationRepository.findByReceiverAndReadStatusFalse(receiver);
    }

    @Override
    public List<Notification> getAllNotifications(User receiver) {
        return notificationRepository.findByReceiver(receiver);
    }

    @Override
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setReadStatus(true);
            notificationRepository.save(n);
        });
    }
}