package com.example.myforum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myforum.model.Notification;
import com.example.myforum.model.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverAndReadStatusFalse(User receiver);
    List<Notification> findByReceiver(User receiver);
}