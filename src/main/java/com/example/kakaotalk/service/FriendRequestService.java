package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.FriendRequest;
import com.example.kakaotalk.entity.User;
import java.util.List;
import java.util.Optional;

public interface FriendRequestService {
    /**
     * 친구 요청 보내기
     */
    FriendRequest sendRequest(Long senderId, Long receiverId);

    /**
     * 받은 친구 요청 목록 조회
     */
    List<FriendRequest> getReceivedRequests(Long userId);

    /**
     * 보낸 친구 요청 목록 조회
     */
    List<FriendRequest> getSentRequests(Long userId);

    /**
     * 친구 요청 수락/거절
     */
    FriendRequest respondToRequest(Long requestId, String status);
}
