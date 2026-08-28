package com.henrique.chat_api.dtos.chat;

import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.UserAccount;

import java.time.Instant;

public record ChatResponseDTO(
        Long chatID,
        UserResponseDTO friend
) {
}
