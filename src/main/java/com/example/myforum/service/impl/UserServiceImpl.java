package com.example.myforum.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password."));
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRole()));
    }

    @Override
    public User register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUsername(userRegisterDto.getUsername()))
            throw new RuntimeException("Username exists");
        if (userRepository.findByEmail(userRegisterDto.getEmail()).isPresent())
            throw new RuntimeException("Email already exists");

        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setPhone(userRegisterDto.getPhone());
        user.setEmail(userRegisterDto.getEmail());
        user.setPassword(encoder.encode(userRegisterDto.getPassword()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    public UserProfileDto getProfileDto(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new UserProfileDto(user.getUsername(), user.getEmail());
    }

    @Override
    public void updateProfile(String username, UserProfileDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setUsername(dto.getFullName());
        user.setEmail(dto.getEmail());
        userRepository.save(user);
    }

    @Override
    public void changePassword(String username, PasswordChangeDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (!encoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        user.setPassword(encoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void updateLocks(String name, boolean lockComments, boolean lockProfile) {
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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
        return userRepository.findByUsername(username).map(user -> {
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }).orElse(false);
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(UserRole role) {
        return AuthorityUtils.createAuthorityList(role.name());
    }
}
