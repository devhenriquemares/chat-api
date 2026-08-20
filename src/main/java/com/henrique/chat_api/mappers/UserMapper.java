package com.henrique.chat_api.mappers;

import com.henrique.chat_api.dtos.user.UserResponse;
import com.henrique.chat_api.entities.UserAccount;

public class UserMapper {
    public static UserResponse toResponse(UserAccount user) {
        return new UserResponse(user.getUsername(), user.getEmail(), user.getProvider(), user.getCreatedAt());
    }
}
