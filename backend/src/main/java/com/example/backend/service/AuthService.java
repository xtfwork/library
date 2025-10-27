package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.backend.repo.UserRepository;
import com.example.backend.support.ConflictException;
import com.example.backend.web.dto.UserProfileDto;
import com.example.backend.web.dto.UserRegisterRequest;
import com.example.backend.domain.User;

import jakarta.transaction.Transactional;

@Service
public class AuthService {
    

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public UserProfileDto register(UserRegisterRequest req) {

        String username = req.username().trim();
        String email = req.email().trim().toLowerCase();

        if(userRepo.existsByUsername(username)){
            throw new ConflictException("username already exists");
        }

        if(userRepo.existsByEmail(email)){
            throw new ConflictException("email already exists");
        }

        User user = User.builder()
            .username(username)
            .email(email)
            .passwordHash(passwordEncoder.encode(req.password()))
            .role("USER")
            .build();
        user = userRepo.save(user);

        return new UserProfileDto(user.getId(), user.getUsername(), user.getEmail(), user.getRole());
    }

}
