package com.henrique.chat_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Random;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "email_codes")
public class EmailCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "code_owner_id", nullable = false)
    private UserAccount codeOwner;

    @Column(unique = true, nullable = false, length = 6)
    private String code;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Column(name = "expires_at")
    private Instant expiresAt = Instant.now().plusSeconds(300);

    @Column(name = "is_expired")
    private boolean isExpired = false;

    public static String generateCode() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "1234567890";

        return new Random()
                .ints(6, 0, CHARACTERS.length())
                .mapToObj(CHARACTERS::charAt)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }
}
