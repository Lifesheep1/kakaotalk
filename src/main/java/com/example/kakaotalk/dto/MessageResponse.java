package com.example.kakaotalk.dto;

import com.example.kakaotalk.entity.Message;

import java.time.LocalDateTime;

/**
 * MessageResponse: 서버가 클라이언트에 메시지 정보를 반환할 때 사용
 * 필드:
 *  - messageId: 고유 메시지 식별자
 *  - roomId: 속한 채팅방 ID
 *  - senderId: 보낸 사람의 사용자 ID
 *  - content: 메시지 내용
 *  - messageType: TEXT, IMAGE 등 메시지 유형
 *  - createdAt: 생성 시점 타임스탬프
 */
public record MessageResponse(
        Long messageId,
        Long roomId,
        Long senderId,
        String content,
        String messageType,
        LocalDateTime createdAt
) {
    /**
     * 엔티티 Message -> DTO 변환 유틸리티 메서드
     */
    public static MessageResponse of(Message message) {
        return new MessageResponse(
                message.getMessageId(),                // DB에서 생성된 메시지 PK
                message.getRoom().getRoomId(),         // 메시지가 속한 방 ID
                message.getSender().getUserId(),       // 보낸 사람 ID
                message.getContent(),                  // 실제 텍스트/콘텐츠
                message.getMessageType(),              // 메시지 유형
                message.getCreatedAt()                 // 저장 시점
        );
    }
}
