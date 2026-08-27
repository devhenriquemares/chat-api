package com.henrique.chat_api.dtos.auth;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ValidateEmailDTO(
        @Size(min = 6, max = 6)
        String code
) {
}
