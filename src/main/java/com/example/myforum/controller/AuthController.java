package com.example.myforum.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.myforum.dto.UserRegisterDto;
import com.example.myforum.model.User;
import com.example.myforum.service.UserService;
import com.example.myforum.util.JwtUtil;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String showLogin() {
        try {
            return "login";
        } catch (Exception e) {
            // Optionally log the error
            return "error";
        }
    }

    @PostMapping("/login")
    public Map<String, String> createAuthenticationToken(@RequestBody Map<String, String> authRequest) throws Exception {
        String email = authRequest.get("email");
        String password = authRequest.get("password");
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (AuthenticationException e) {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtUtil.generateToken(userDetails);

        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);
        return response;
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        try {
            UserRegisterDto user = new UserRegisterDto();
            model.addAttribute("user", user);
            return "register";
        } catch (Exception e) {
            // Optionally log the error
            model.addAttribute("error", "An error occurred while loading the registration page.");
            return "error";
        }
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") UserRegisterDto userRegisterDto, Model model,
            BindingResult result) {
        try {
            User existingUser = userService.findByEmail(userRegisterDto.getEmail());
            if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
                result.rejectValue("email", null, "There is already an account registered with the same email");
            }
            if (result.hasErrors()) {
                model.addAttribute("user", userRegisterDto);
                return "/register";
            }
            userService.saveUser(userRegisterDto);
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
