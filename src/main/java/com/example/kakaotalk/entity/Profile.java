package com.example.kakaotalk.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 2083)
    private String profileImgUrl;

    @Column(length = 2083)
    private String backgroundImgUrl;

    @Column(length = 255)
    private String statusMessage;

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();
}