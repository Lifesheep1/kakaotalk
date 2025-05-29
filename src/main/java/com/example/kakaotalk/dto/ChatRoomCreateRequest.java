package com.example.kakaotalk.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 채팅방 생성 요청 DTO
 * @param roomType
 * @param memberIds
 */
public record ChatRoomCreateRequest(
        @NotNull String roomType,
        @NotEmpty List<Long> memberIds
) {
}
