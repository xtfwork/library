package com.example.backend.web.dto;

public record UserProfileDto(
    Long id,
    String username,
    String email,
    String role
) {
}
