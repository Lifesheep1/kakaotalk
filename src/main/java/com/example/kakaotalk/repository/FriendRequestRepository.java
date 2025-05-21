package com.example.kakaotalk.repository;

import com.example.kakaotalk.entity.FriendRequest;
import com.example.kakaotalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 친구 요청 관리
 */
@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findByReceiverAndStatus(User receiver, String status);
    List<FriendRequest> findBySender (User sender);
    Optional<FriendRequest> findBySenderAndReceiver (User sender, User receiver);
}
