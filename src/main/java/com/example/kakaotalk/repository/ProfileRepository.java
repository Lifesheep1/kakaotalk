package com.example.kakaotalk.repository;

import com.example.kakaotalk.entity.Profile;
import com.example.kakaotalk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Profile 관련 CRUD
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
}
