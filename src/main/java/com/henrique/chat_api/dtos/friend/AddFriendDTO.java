package com.henrique.chat_api.dtos.friend;

import jakarta.validation.constraints.Size;

public record AddFriendDTO(
        @Size(min = 10, max = 10)
        String publicID
) {
}
