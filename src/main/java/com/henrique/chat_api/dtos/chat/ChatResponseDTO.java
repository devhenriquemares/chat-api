package com.henrique.chat_api.dtos.chat;

import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.Message;
import com.henrique.chat_api.entities.UserAccount;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public record ChatResponseDTO(
        Long chatID,
        UserResponseDTO friend,
        List<Message> messages
) {
}
