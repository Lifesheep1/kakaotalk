package com.example.kakaotalk.dto;

import com.example.kakaotalk.entity.User;
import java.time.LocalDateTime;

public record UserResponse(
        Long userId,
        String username,
        String email,
        LocalDateTime createdAt,
        LocalDateTime lastLogin
) {
    public static UserResponse fromEntity(User u) {
        return new UserResponse(
                u.getUserId(),
                u.getUsername(),
                u.getEmail(),
                u.getCreatedAt(),
                u.getLastLogin()
        );
    }
}
