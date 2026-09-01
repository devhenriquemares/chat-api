package com.henrique.chat_api.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "friend_requests")
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "sender_id")
    private UserAccount sender;

    @ManyToOne
    @JoinColumn(nullable = false, name = "recipient_id")
    private UserAccount recipient;
}
