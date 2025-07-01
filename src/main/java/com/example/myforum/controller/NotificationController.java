package com.example.myforum.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.myforum.model.Notification;
import com.example.myforum.model.User;
import com.example.myforum.service.NotificationService;
import com.example.myforum.service.UserService;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

    @GetMapping("/notifications")
    public String notifications(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Notification> notifications = notificationService.getAllNotifications(user);
        model.addAttribute("notifications", notifications);
        return "client/notifications";
    }
}
