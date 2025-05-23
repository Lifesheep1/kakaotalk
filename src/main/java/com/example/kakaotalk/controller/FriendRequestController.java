package com.example.kakaotalk.controller;

import com.example.kakaotalk.dto.FriendRequestCreateRequest;
import com.example.kakaotalk.dto.FriendRequestResponse;
import com.example.kakaotalk.entity.FriendRequest;
import com.example.kakaotalk.service.FriendRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/friends/requests")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    //친구 요청 보내기
    @PostMapping
    public ResponseEntity<FriendRequestResponse> send(@Valid @RequestBody FriendRequestCreateRequest dto){
        FriendRequest friendRequest = friendRequestService.sendRequest(dto.senderId(), dto.receiverId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(FriendRequestResponse.of(friendRequest));
    }

    //받은 요청 조회
    @GetMapping("/received")
    public ResponseEntity<List<FriendRequestResponse>> received(@RequestParam Long userId){
        List<FriendRequestResponse> list = friendRequestService.getReceivedRequests(userId).stream()
                .map(FriendRequestResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    //보낸 요청 조회
    @GetMapping("/sent")
    public ResponseEntity<List<FriendRequestResponse>> sent(@RequestParam Long userId){
        List<FriendRequestResponse> list = friendRequestService.getSentRequests(userId).stream()
                .map(FriendRequestResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    //요청 수락/거절
    @PutMapping("/{id}")
    public ResponseEntity<FriendRequestResponse> respond(@PathVariable Long id,
                                                 @RequestParam String status){
        FriendRequest updated = friendRequestService.respondToRequest(id, status);
        return ResponseEntity.ok(FriendRequestResponse.of(updated));
    }
}
