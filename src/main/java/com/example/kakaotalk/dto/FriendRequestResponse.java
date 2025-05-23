package com.example.kakaotalk.dto;

import com.example.kakaotalk.entity.FriendRequest;

import java.time.LocalDateTime;

public record FriendRequestResponse(
        Long requestId,
        Long senderId,
        String senderName,
        Long receiverId,
        String receiverName,
        String status,
        LocalDateTime createdAt
) {
    public static FriendRequestResponse of(FriendRequest friendRequest) {
        return new FriendRequestResponse(
                friendRequest.getRequestId(),
                friendRequest.getSender().getUserId(),
                friendRequest.getSender().getUsername(),
                friendRequest.getReceiver().getUserId(),
                friendRequest.getReceiver().getUsername(),
                friendRequest.getStatus(),
                friendRequest.getCreatedAt()
        );
    }
}
