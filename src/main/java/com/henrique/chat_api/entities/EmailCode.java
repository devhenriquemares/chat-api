package com.henrique.chat_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "email_codes")
public class EmailCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "code_owner", nullable = false)
    private UserAccount codeOwner;

    @Column(unique = true, nullable = false, length = 6)
    private String code;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "expires_at")
    private Instant expiresAt = Instant.now().plusSeconds(300);

    @Column(name = "is_expired")
    private boolean isExpired = false;
}
