package com.example.kakaotalk.repository;

import com.example.kakaotalk.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 채팅방 CRUD
 */
@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 유저별 참여 채팅방 조회 (멤버십을 통해)
}
