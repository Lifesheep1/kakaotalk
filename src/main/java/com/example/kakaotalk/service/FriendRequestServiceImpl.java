package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.FriendRequest;
import com.example.kakaotalk.entity.User;
import com.example.kakaotalk.repository.FriendRequestRepository;
import com.example.kakaotalk.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public FriendRequest sendRequest(Long senderId, Long receiverId) {
        if (senderId.equals(receiverId)) {
            throw new IllegalArgumentException("Cannot friend yourself");
        }
        User sender = userRepository.findById(senderId).orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        //중복 요청 방지
        Optional<FriendRequest> existing = friendRequestRepository.findBySenderAndReceiver(sender, receiver);
        if (existing.isPresent()) {
            throw new IllegalStateException("Request already sent");
        }
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(sender);
        friendRequest.setReceiver(receiver);
        friendRequest.setStatus("PENDING");
        friendRequest.setCreatedAt(LocalDateTime.now());
        return friendRequestRepository.save(friendRequest);
    }

    @Override
    public List<FriendRequest> getReceivedRequests(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return friendRequestRepository.findByReceiverAndStatus(user, "PENDING");
    }

    @Override
    public List<FriendRequest> getSentRequests(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return friendRequestRepository.findBySender(user);
    }
    @Override
    @Transactional
    public FriendRequest respondToRequest(Long requestId, String status) {
        FriendRequest req = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
        if (!req.getStatus().equals("PENDING")) {
            throw new IllegalStateException("Request already responded");
        }
        if (!List.of("ACCEPTED","REJECTED").contains(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        req.setStatus(status);
        return friendRequestRepository.save(req);
    }


}
