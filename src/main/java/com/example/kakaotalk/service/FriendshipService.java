package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.Friendship;

import java.util.List;

public interface FriendshipService {

    //친구관계 생성 (양쪽모두)
    Friendship addFriend(Long userId, Long friendId);
    //특정 유저의 친구 목록 조회
    List<Friendship> listFriends(Long userId);
    //친구 관계 삭제
    void removeFriend(Long friendId);
}
