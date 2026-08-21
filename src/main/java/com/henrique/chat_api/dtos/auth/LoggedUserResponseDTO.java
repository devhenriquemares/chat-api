package com.henrique.chat_api.dtos.auth;

import com.henrique.chat_api.dtos.TokensDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;

public record LoggedUserResponseDTO(
        TokensDTO tokens,
        UserResponseDTO userResponse
) {
}
