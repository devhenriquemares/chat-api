package com.henrique.chat_api.dtos;

public record TokensDTO(
        String accessToken,
        String refreshToken
) {
}
