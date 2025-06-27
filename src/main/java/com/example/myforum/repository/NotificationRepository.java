package com.example.myforum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.myforum.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByRecipientAndReadFalse(String recipient);
}