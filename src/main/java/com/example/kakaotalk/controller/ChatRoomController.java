package com.example.kakaotalk.controller;

import com.example.kakaotalk.dto.ChatRoomCreateRequest;
import com.example.kakaotalk.dto.ChatRoomResponse;
import com.example.kakaotalk.entity.ChatRoom;
import com.example.kakaotalk.service.ChatRoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/chatrooms")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    public ChatRoomController(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    /**
     * 채팅방 생성
     * @param request
     * @return
     */
    @PostMapping
    public ResponseEntity<ChatRoomResponse> createRoom(
            @Valid @RequestBody ChatRoomCreateRequest request) {
        ChatRoom chatRoom = chatRoomService.createChatRoom(request.roomType(), request.membersIds());
        ChatRoomResponse response = ChatRoomResponse.of(chatRoom);
        return ResponseEntity.created(URI.create("/api/chatrooms/" + chatRoom.getRoomId()))
                .body( response);
    }

    /**
     * 채팅방에 유저 초대
     * @param roomId
     * @param userId
     * @return
     */
    @PostMapping("/{roomId}/members")
    public ResponseEntity<Void> addMember(
            @PathVariable Long roomId,
            @RequestParam Long userId) {
        chatRoomService.addMember(roomId, userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * 채팅방 멤버 제거
     * @param roomId
     * @param membershipId
     * @return
     */
    @DeleteMapping("/{roomId}/members/{membershipId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable Long roomId,
            @PathVariable Long membershipId) {
        chatRoomService.removeMember(roomId, membershipId);
        return ResponseEntity.noContent().build();
    }
}
