package com.example.kakaotalk.controller;

import com.example.kakaotalk.dto.FriendshipResponse;
import com.example.kakaotalk.entity.Friendship;
import com.example.kakaotalk.service.FriendshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/friends")
public class FriendShipController {

    private final FriendshipService friendshipService;
    public FriendShipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    /**
     * 친구 추가
     * @param userId
     * @param friendId
     * @return
     */
    @PostMapping("/{userId}/{friendId}")
    public ResponseEntity<FriendshipResponse> add(
            @PathVariable Long userId,
            @PathVariable Long friendId) {
        Friendship friendship = friendshipService.addFriend(userId, friendId);
        return ResponseEntity.ok(FriendshipResponse.of(friendship));
    }

    /**
     * 친구목록 조회
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<FriendshipResponse>> list(@PathVariable Long userId) {
        List<FriendshipResponse> list = friendshipService.listFriends(userId).stream()
                .map(FriendshipResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    /**
     * 친구 삭제
     * @param friendshipId
     * @return
     */
    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<Void> remove(@PathVariable Long friendshipId) {
        friendshipService.removeFriend(friendshipId);
        return ResponseEntity.noContent().build();
    }


}
