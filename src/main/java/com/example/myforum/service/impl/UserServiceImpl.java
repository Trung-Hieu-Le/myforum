package com.example.myforum.service.impl;

import com.example.myforum.model.User;
import com.example.myforum.dto.PasswordChangeDto;
import com.example.myforum.dto.UserProfileDto;
import com.example.myforum.enums.UserRole;
import com.example.myforum.repository.UserRepository;
import com.example.myforum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repo;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true, true, true,
                AuthorityUtils.createAuthorityList(user.getRole().name()));
    }

    @Override
    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);
        repo.save(user);
    }

    @Override
    public UserProfileDto getProfileDto(String username) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserProfileDto(user.getUsername(), user.getEmail());
    }

    @Override
    public void updateProfile(String username, UserProfileDto dto) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setUsername(dto.getFullName());
        user.setEmail(dto.getEmail());
        repo.save(user);
    }

    @Override
    public void changePassword(String username, PasswordChangeDto dto) {
        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!encoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPassword(encoder.encode(dto.getNewPassword()));
        repo.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void updateLocks(String name, boolean lockComments, boolean lockProfile) {
        User user = repo.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setLockComments(lockComments);
        user.setLockProfile(lockProfile);
        repo.save(user);
    }
    
}
