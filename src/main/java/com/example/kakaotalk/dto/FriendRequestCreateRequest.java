package com.example.kakaotalk.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 친구 요청 생성용 DTO
 */
public record FriendRequestCreateRequest(
        @NotNull @Min(1) Long senderId,
        @NotNull @Min(1) Long receiverId
) {}