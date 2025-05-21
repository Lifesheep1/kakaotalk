package com.example.kakaotalk.dto;

public record UserRegisterRequest(
        String username,
        String email,
        String password
) {}