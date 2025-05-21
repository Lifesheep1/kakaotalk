package com.example.kakaotalk.repository;

import com.example.kakaotalk.entity.ChatRoom;
import com.example.kakaotalk.entity.ChatRoomMember;
import com.example.kakaotalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 채팅방 참여자 정보
 */
@Repository
public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
    List<ChatRoomMember> findByRoom(ChatRoom room);
    List<ChatRoomMember> findByUser(User user);
    Optional<ChatRoomMember> findByRoomAndUser(ChatRoom room, User user);
}
