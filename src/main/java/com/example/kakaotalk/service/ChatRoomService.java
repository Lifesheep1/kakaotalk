package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.ChatRoom;

import java.util.List;

public interface ChatRoomService {
    /**
     * 새로운 채팅방 생성
     */
    ChatRoom createChatRoom(String roomType, List<Long> memberIds);

    /**
     * 유저가 속한 채팅방 목록 조회
     */
    List<ChatRoom> getRoomsForUser(Long userId);

    /**
     * 채팅방 참여자 추가
     */
    void addMember(Long roomId, Long userId);

    /**
     * 채팅방 참여자 제거
     */
    void removeMember(Long roomId, Long membershipId);
}
