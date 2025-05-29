package com.example.kakaotalk.dto;

import com.example.kakaotalk.entity.ChatRoomMember;

import java.time.LocalDateTime;

/**
 * 채팅방 참여자 응답 DTO
 * @param membershipId
 * @param userId
 * @param username
 * @param joinedAt
 */
public record ChatRoomMemberResponse(
        Long membershipId,
        Long userId,
        String username,
        LocalDateTime joinedAt
) {
    public static ChatRoomMemberResponse of(ChatRoomMember member) {
        return new ChatRoomMemberResponse(
                member.getMembershipId(),
                member.getUser().getUserId(),
                member.getUser().getUsername(),
                member.getJoinedAt()
        );
    }
}
