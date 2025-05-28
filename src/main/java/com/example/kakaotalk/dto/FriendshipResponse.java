package com.example.kakaotalk.dto;

import com.example.kakaotalk.entity.Friendship;

import java.time.LocalDate;

// 친구 관계 응답용 dto
public record FriendshipResponse(
        Long friendshipId,
        Long userId,
        String username,
        LocalDate since
) {
    public static FriendshipResponse of(Friendship friendship) {
        return new FriendshipResponse(
                friendship.getFriendshipId(),
                friendship.getFriend().getUserId(),
                friendship.getFriend().getUsername(),
                friendship.getSince() != null
                        ? friendship.getSince()
                        : LocalDate.now()
        );
    }
}
