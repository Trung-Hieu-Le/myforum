package com.example.myforum.service;

import com.example.myforum.dto.PasswordChangeDto;
import com.example.myforum.dto.UserProfileDto;
import com.example.myforum.dto.UserRegisterDto;
import com.example.myforum.model.User;

public interface UserService {
    User saveUser(UserRegisterDto userRegisterDto);

    UserProfileDto getProfileDto(String username);

    void updateProfile(String username, UserProfileDto dto);

    void changePassword(String username, PasswordChangeDto dto);

    User findByUsername(String username);

    User findByEmail(String email);

    void updateLocks(String name, boolean lockComments, boolean lockProfile);

    boolean verifyUsernameAndEmail(String username, String email);

    boolean resetPassword(String username, String newPassword);
}
