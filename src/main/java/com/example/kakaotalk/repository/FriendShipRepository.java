package com.example.kakaotalk.repository;


import com.example.kakaotalk.entity.Friendship;
import com.example.kakaotalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 친구 관계 (친구 목록)
 */
@Repository
public interface FriendShipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUser (User user);
    Optional<Friendship> findByUserAndFriend (User user, User friend);
}
