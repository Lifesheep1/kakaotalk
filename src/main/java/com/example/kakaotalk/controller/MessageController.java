package com.example.kakaotalk.controller;

import com.example.kakaotalk.dto.MessageCreateRequest;
import com.example.kakaotalk.dto.MessageResponse;
import com.example.kakaotalk.entity.Message;
import com.example.kakaotalk.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MessageController: 메시지 관련 REST API 제공
 */
@RestController
@RequestMapping("/api/rooms/{roomId}/messages")
public class MessageController {
    private final MessageService messageService;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 메시지 전송 엔드포인트
     * @PathVariable roomId: URL 경로에서 방 ID 추출
     * @Valid @RequestBody req: Body 검증 적용
     * @return 저장된 메시지 정보
     */
    @PostMapping
    public ResponseEntity<MessageResponse> sendMessage(
            @PathVariable Long roomId,
            @Valid @RequestBody MessageCreateRequest request) {
        Message message = messageService.createMessage(
                roomId,
                request.senderId(),
                request.content(),
                request.messageType()
        );
        return ResponseEntity.ok(MessageResponse.of(message));
    }

    /**
     * 채팅방 메시지 조회 엔드포인트
     * @RequestParam(defaultValue = "50") int limit:
     *   - 요청 시 limit 파라미터가 없으면 기본값 50으로 사용
     *   - 가져올 메시지 개수 제한용
     * @RequestParam(required = false) @DateTimeFormat:
     *   - before 파라미터가 없으면 전체 메시지 중 최신 limit 개를 조회
     *   - ISO 형식(yyyy-MM-dd'T'HH:mm:ss)로 파싱
     */
    @GetMapping
    public ResponseEntity<List<MessageResponse>> getMessages(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "50") int limit,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime before
    ) {
        List<MessageResponse> list = messageService.getMessages(roomId, before, limit)
                .stream().map(MessageResponse::of).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
