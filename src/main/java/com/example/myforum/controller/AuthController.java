package com.example.myforum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myforum.dto.UserRegisterDto;
import com.example.myforum.service.UserService;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute UserRegisterDto userRegisterDto, Model model) {
        try {
            userService.register(userRegisterDto);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage(Model model) {
        model.addAttribute("page", "forgot-password");
        return "client/home";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam String username,
            @RequestParam String email,
            Model model) {
        try {
            if (userService.verifyUsernameAndEmail(username, email)) {
                model.addAttribute("page", "reset-password");
                model.addAttribute("username", username);
            } else {
                model.addAttribute("error", "The username or email is incorrect");
                model.addAttribute("page", "forgot-password");
            }
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while verifying your information.");
            model.addAttribute("page", "forgot-password");
        }
        return "client/home";
    }

    @PostMapping("/reset-password")
    public String handleResetPassword(@RequestParam String username,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {
        try {
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
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while resetting your password.");
            model.addAttribute("page", "reset-password");
            model.addAttribute("username", username);
            return "client/home";
        }
    }
}
