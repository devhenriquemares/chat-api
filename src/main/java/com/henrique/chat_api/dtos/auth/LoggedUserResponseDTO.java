package com.henrique.chat_api.dtos.auth;

import com.henrique.chat_api.dtos.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response from AuthController routes")
public record LoggedUserResponseDTO(
        TokensDTO tokens,
        UserResponseDTO userResponse
) { }