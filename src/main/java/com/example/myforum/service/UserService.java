package com.example.myforum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myforum.model.User;
import com.example.myforum.enums.UserRole;
import com.example.myforum.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder encoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.getEnabled(),
            true, true, true,
            AuthorityUtils.createAuthorityList(user.getRole().name())
        );
    }
    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(UserRole.USER);
        user.setEnabled(true);
        repo.save(user);
    }
}

