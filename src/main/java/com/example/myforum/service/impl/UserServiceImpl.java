package com.example.myforum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myforum.dto.PasswordChangeDto;
import com.example.myforum.dto.UserProfileDto;
import com.example.myforum.dto.UserRegisterDto;
import com.example.myforum.enums.UserRole;
import com.example.myforum.model.User;
import com.example.myforum.repository.UserRepository;
import com.example.myforum.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
            .map(user -> org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .accountLocked(!user.getEnabled())
                .build())
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public User saveUser(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setEmail(userRegisterDto.getEmail());
        user.setPhone(userRegisterDto.getPhone());
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public void updateProfile(String username, UserProfileDto dto) {
        User user = userRepository.findByUsername(username);
        user.setUsername(dto.getFullName());
        user.setEmail(dto.getEmail());
        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, PasswordChangeDto dto) {
        User user = userRepository.findByUsername(username);
        if (!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void updateLocks(String name, boolean lockComments, boolean lockProfile) {
        User user = userRepository.findByUsername(name);
        user.setLockComments(lockComments);
        user.setLockProfile(lockProfile);
        userRepository.save(user);
    }

    @Override
    public boolean verifyUsernameAndEmail(String username, String email) {
        return userRepository.findByUsernameAndEmail(username, email).isPresent();
    }

    @Override
    public boolean resetPassword(String username, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public UserProfileDto getProfileDto(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProfileDto'");
    }
}
