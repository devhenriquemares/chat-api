package com.henrique.chat_api.dtos.friend;

import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.UserAccount;

public record FriendResponseDTO(
        UserResponseDTO friend
) {
}
