package com.example.kakaotalk.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * MessageCreateRequest: 클라이언트가 메시지를 전송할 때 사용하는 요청 DTO.
 * @NotNull: null 값 허용 안 함
 * @Size(min=1, max=20): 메시지 타입은 최소 1자, 최대 20자까지 허용
 */
public record MessageCreateRequest(
        @NotNull Long senderId,
        @NotNull String content,
        @NotNull @Size(min = 1 , max = 20) String messageType
) {
}
