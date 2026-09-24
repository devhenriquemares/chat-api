package com.henrique.chat_api.dtos.friend;

import com.henrique.chat_api.dtos.user.UserResponseDTO;

public record FriendRequestResponseDTO(
        Long requestID,
        UserResponseDTO requester
) {
}
