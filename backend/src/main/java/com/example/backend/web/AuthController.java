package com.example.backend.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.backend.service.AuthService;
import com.example.backend.web.dto.UserProfileDto;
import com.example.backend.web.dto.UserRegisterRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public UserProfileDto register_user(@Valid @RequestBody UserRegisterRequest req) {
        return authService.register(req);
    }

}
