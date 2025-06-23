package com.example.myforum.controller;

import com.example.myforum.model.User;
import com.example.myforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model) {
        model.addAttribute("page", "forgot-password");
        return "client/home"; // hoặc template bạn đang dùng
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String username,
            @RequestParam String email,
            Model model) {
        if (userService.verifyUsernameAndEmail(username, email)) {
            model.addAttribute("page", "reset-password");
            model.addAttribute("username", username);
        } else {
            model.addAttribute("error", "The username or email is incorrect");
            model.addAttribute("page", "forgot-password");
        }
        return "client/home";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam String username,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu không khớp");
            model.addAttribute("page", "reset-password");
            model.addAttribute("username", username);
            return "client/home";
        }

        if (userService.resetPassword(username, newPassword)) {
            return "redirect:/login?resetSuccess";
        } else {
            model.addAttribute("error", "Đổi mật khẩu thất bại");
            model.addAttribute("page", "reset-password");
            model.addAttribute("username", username);
            return "client/home";
        }
    }
}
