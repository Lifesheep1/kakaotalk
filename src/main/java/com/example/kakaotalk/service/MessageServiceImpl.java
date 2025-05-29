package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.ChatRoom;
import com.example.kakaotalk.entity.Message;
import com.example.kakaotalk.entity.User;
import com.example.kakaotalk.repository.ChatRoomRepository;
import com.example.kakaotalk.repository.MessageRepository;
import com.example.kakaotalk.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
/**
 * MessageService 구현체
 * @Transactional(readOnly=true): 메서드 기본은 읽기 전용 트랜잭션
 */
@Service
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService{
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, ChatRoomRepository chatRoomRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
    }
    /**
     * 메시지 생성 로직
     * @Transactional: 쓰기 전용 트랜잭션
     */
    @Override
    @Transactional
    public Message createMessage(Long roomId, Long senderId, String content, String messageType){
        // 1) 채팅방 유무 확인
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found" + roomId));
        // 2) 송신자 유무 확인
        User sender = userRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("User not found" + senderId));

        // 3) 엔티티 생성
        Message message = new Message();
        message.setRoom(room);
        message.setSender(sender);
        message.setContent(content);
        message.setMessageType(messageType);
        message.setCreatedAt(LocalDateTime.now());
        // 4) DB 저장
        return messageRepository.save(message);
    }
    /**
     * 메시지 조회
     * - before != null: before 시점 이전 메시지를 내림차순으로 받아온 뒤 limit 개수만큼 리턴
     * - before == null: 전체 메시지 오름차순 조회 후 최신 limit 개수만큼 슬라이싱
     */
    @Override
    public List<Message> getMessages(Long roomId, LocalDateTime before, int limit){
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found" + roomId));
        if (before != null) {
            // before 시점 이전 메시지 내림차순 OrderByCreatedAtDesc
            return messageRepository.findByRoomAndCreatedAtBeforeOrderByCreatedAtDesc(room, before)
                    .stream().limit(limit).toList();
        } else {
            // 전체 메시지 오름차순 조회
            List<Message> all = messageRepository.findByRoomOrderByCreatedAtAsc(room);
            // 최신 limit 개만 추출 (skip 계산)
            int start = Math.max(0, all.size() - limit);
            return all.subList(start, all.size());
        }
    }
}
