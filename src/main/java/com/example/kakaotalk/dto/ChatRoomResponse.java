package com.example.kakaotalk.dto;

import com.example.kakaotalk.entity.ChatRoom;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 채팅방 응답 DTO
 * @param roomId
 * @param roomType
 * @param createdAt
 * @param members
 */
public record ChatRoomResponse(
        Long roomId,
        String roomType,
        LocalDateTime createdAt,
        List<ChatRoomMemberResponse> members
) {
    public static ChatRoomResponse of(ChatRoom room) {
        List<ChatRoomMemberResponse> list = room.getMembers().stream()
                .map(ChatRoomMemberResponse::of)
                .toList();
        return new ChatRoomResponse(
                room.getRoomId(),
                room.getRoomType(),
                room.getCreatedAt(),
                list
        );
    }
}
