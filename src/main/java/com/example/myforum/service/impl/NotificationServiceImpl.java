package com.example.myforum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myforum.model.Notification;
import com.example.myforum.repository.NotificationRepository;
import com.example.myforum.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void sendNotification(String recipient, String message) {
        Notification notification = new Notification();
        notification.setReceiver(recipient);
        notification.setContent(message);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getUnreadNotifications(String recipient) {
        return notificationRepository.findByRecipientAndReadFalse(recipient);
    }

    @Override
    public void markAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(n -> {
            n.setReadStatus(true);
            notificationRepository.save(n);
        });
    }
}