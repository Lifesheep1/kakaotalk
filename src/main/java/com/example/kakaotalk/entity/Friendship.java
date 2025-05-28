package com.example.kakaotalk.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter@Setter
@Table(name = "friendship",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id","friend_id"}))
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendshipId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friend;

    @Column(nullable = false)
    private LocalDate since;
}
