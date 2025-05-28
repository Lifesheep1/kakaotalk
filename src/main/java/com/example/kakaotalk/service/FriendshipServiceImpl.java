package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.Friendship;
import com.example.kakaotalk.entity.User;
import com.example.kakaotalk.repository.FriendShipRepository;
import com.example.kakaotalk.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendShipRepository friendShipRepository;
    private final UserRepository userRepository;

    public FriendshipServiceImpl(FriendShipRepository friendShipRepository, UserRepository userRepository) {
        this.friendShipRepository = friendShipRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Friendship addFriend(Long userId, Long friendId) {
        if(userId.equals(friendId)) {
            throw new IllegalArgumentException("Cannot friend yourself");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        User friend = userRepository.findById(friendId).orElseThrow(() -> new IllegalArgumentException("Friend not found"));

        // 1) A→B 레코드가 없을 때만 생성
        Optional<Friendship> existing1 = friendShipRepository.findByUserAndFriend(user, friend);
        if (existing1.isEmpty()) {
            Friendship f1 = new Friendship();
            f1.setUser(user);
            f1.setFriend(friend);
            f1.setSince(LocalDate.now());
            friendShipRepository.save(f1);
        }

        // 2) B→A 레코드가 없을 때만 생성
        Optional<Friendship> existing2 = friendShipRepository.findByUserAndFriend(friend, user);
        if (existing2.isEmpty()) {
            Friendship f2 = new Friendship();
            f2.setUser(friend);
            f2.setFriend(user);
            f2.setSince(LocalDate.now());
            friendShipRepository.save(f2);
        }

        // 반환할 레코드는 A→B 첫 번째 것을 가져와도 되고, existing1이 있으면 그것을 반환해도 됩니다.
        return existing1.orElseGet(() -> friendShipRepository.findByUserAndFriend(user, friend).get());
    }

    @Override
    public List<Friendship> listFriends(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return friendShipRepository.findByUser(user);
    }

    @Override
    @Transactional
    public void removeFriend(Long friendshipId){
        Friendship friendship = friendShipRepository.findById(friendshipId)
                .orElseThrow(() -> new IllegalArgumentException("Friend not found"));
        //상대 편도 삭제
        friendShipRepository.findByUserAndFriend(friendship.getFriend(), friendship.getUser())
                .ifPresent(friendShipRepository::delete);
        friendShipRepository.delete(friendship);
    }
}
