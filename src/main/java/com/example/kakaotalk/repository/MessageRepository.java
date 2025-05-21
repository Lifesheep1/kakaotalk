package com.example.kakaotalk.repository;

import com.example.kakaotalk.entity.ChatRoom;
import com.example.kakaotalk.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 메시지 저장·조회
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomOrderByCreatedAtAsc(ChatRoom room);
    List<Message> findByRoomAndCreatedAtBeforeOrderByCreatedAtDesc(ChatRoom room, java.time.LocalDateTime before);
}