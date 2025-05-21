package com.example.kakaotalk.service;

import com.example.kakaotalk.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * 사용자(User) 관련 비즈니스 로직 처리
 */
public interface UserService {

    /**
     * 신규 사용자 등록
     * @param username 사용자명
     * @param email 이메일(중복 방지)
     * @param password 비밀번호
     * @return 저장된 User 엔티티
     * @throws IllegalArgumentException 중복 이메일의 경우
     */
    User register(String username, String email, String password);

    /**
     * 사용자 ID로 조회
     * @param userId
     * @return
     */
    Optional<User> findById(Long userId);

    /**
     * 이메일로 조회
     * @param email
     * @return
     */
    Optional<User> findByEmail(String email);

    /**
     * 모든 사용자 조회
     * @return
     */
    List<User> findAll();
}
