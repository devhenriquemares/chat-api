package com.henrique.chat_api.mappers;

import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.UserAccount;

public class UserMapper {
    public static UserResponseDTO toResponse(UserAccount user) {
        return new UserResponseDTO(user.getName(), user.getEmail(), user.getProvider(), user.getCreatedAt());
    }
}
