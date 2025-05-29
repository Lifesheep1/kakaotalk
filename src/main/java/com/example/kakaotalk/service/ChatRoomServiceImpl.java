package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.ChatRoom;
import com.example.kakaotalk.entity.ChatRoomMember;
import com.example.kakaotalk.entity.User;
import com.example.kakaotalk.repository.ChatRoomMemberRepository;
import com.example.kakaotalk.repository.ChatRoomRepository;
import com.example.kakaotalk.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ChatRoomServiceImpl implements ChatRoomService{
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserRepository userRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository, ChatRoomMemberRepository chatRoomMemberRepository, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMemberRepository = chatRoomMemberRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ChatRoom createChatRoom(String roomType, List<Long> memberIds) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setRoomType(roomType);
        chatRoom.setCreatedAt(LocalDateTime.now());
        ChatRoom savedchatRoom = chatRoomRepository.save(chatRoom);

        //참여자 추가
        List<ChatRoomMember> members = memberIds.stream()
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("User not found" + id)))
                .map(user -> {
                    ChatRoomMember member = new ChatRoomMember();
                    member.setRoom(savedchatRoom);
                    member.setUser(user);
                    member.setJoinedAt(LocalDateTime.now());
                    return chatRoomMemberRepository.save(member);
                })
                .collect(Collectors.toList());

        savedchatRoom.setMembers(members);
        return chatRoom;
    }

    @Override
    public List<ChatRoom> getRoomsForUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found" + userId));
        return chatRoomMemberRepository.findByUser(user).stream()
                .map(ChatRoomMember::getRoom)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addMember(Long roomId, Long userId) {
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found" + roomId));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found" + userId));
        // 중복 체크
        chatRoomMemberRepository.findByRoomAndUser(room, user).ifPresent(m -> {
            throw new IllegalStateException("User already in room");
        });
        ChatRoomMember member = new ChatRoomMember();
        member.setRoom(room);
        member.setUser(user);
        member.setJoinedAt(LocalDateTime.now());
        chatRoomMemberRepository.save(member);

    }

    @Override
    @Transactional
    public void removeMember(Long roomId, Long membershipId) {
        chatRoomMemberRepository.findById(membershipId)
                .filter(m -> m.getRoom().getRoomId().equals(roomId))
                .orElseThrow(() -> new IllegalArgumentException("Membership not found"));
        chatRoomMemberRepository.deleteById(membershipId);
    }
}
