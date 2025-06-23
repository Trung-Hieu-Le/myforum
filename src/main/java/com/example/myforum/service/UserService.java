package com.example.myforum.service;

import com.example.myforum.dto.PasswordChangeDto;
import com.example.myforum.dto.UserProfileDto;
import com.example.myforum.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void register(User user);
    UserProfileDto getProfileDto(String username);
    void updateProfile(String username, UserProfileDto dto);
    void changePassword(String username, PasswordChangeDto dto);
    User findByUsername(String username);
    void updateLocks(String name, boolean lockComments, boolean lockProfile);
}

