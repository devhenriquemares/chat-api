package com.henrique.chat_api.mappers;

import com.henrique.chat_api.dtos.chat.ChatResponseDTO;
import com.henrique.chat_api.entities.Friend;

public class ChatMapper {
    public static ChatResponseDTO mapToResponse(Friend chat) {
        return new ChatResponseDTO(
            chat.getId(),
            UserMapper.toResponse(chat.getFriendAccount())
        );
    }
}
