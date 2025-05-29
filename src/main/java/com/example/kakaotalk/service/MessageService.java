package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.Message;

import java.time.LocalDateTime;
import java.util.List;
/**
 * MessageService: 메시지 저장 및 조회 로직을 캡슐화
 */
public interface MessageService {
    /**
     * 새로운 메시지를 DB에 저장
     * @param roomId      방 ID
     * @param senderId    보낸 사람 ID
     * @param content     메시지 내용
     * @param messageType 메시지 유형 (예: TEXT)
     * @return 저장된 Message 엔티티
     */
    Message createMessage(Long roomId, Long senderId, String content, String messageType);

    /**
     * 채팅방 메시지 조회
     * @param roomId 채팅방 ID
     * @param before 조회 기준 시점 (null이면 최신부터)
     * @param limit  조회 개수 제한
     * @return Message 리스트
     */
    List<Message> getMessages(Long roomId, LocalDateTime before, int limit);
}
