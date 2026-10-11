package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.auth.*;
import com.henrique.chat_api.dtos.error.ErrorResponseDTO;
import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.services.AuthService;
import com.henrique.chat_api.services.EmailCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Related to all authentication routes such as registration and login")
public class AuthController {
    private final AuthService authService;
    private final EmailCodeService emailCodeService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully registered"),
        @ApiResponse(responseCode = "409", description = "Email already exists",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
    })
    public ResponseEntity<LoggedUserResponseDTO> register(@Valid @RequestBody CreateLocalUserDTO request) {
        LoggedUserResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Login an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in"),
            @ApiResponse(responseCode = "400", description = "Bad credentials on request body",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<LoggedUserResponseDTO> login(@Valid @RequestBody LoginUserDTO request) {
        LoggedUserResponseDTO response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh authentication tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tokens successfully refreshed"),
            @ApiResponse(responseCode = "401", description = "Invalid refresh token",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<LoggedUserResponseDTO> refreshTokens(@Valid @RequestBody RefreshTokensDTO request) {
        LoggedUserResponseDTO response = authService.refreshTokens(request.refreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/email-code")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Validate user's email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email successfully validated"),
            @ApiResponse(responseCode = "400", description = "Email code invalid",
                content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<TokensDTO> validateEmail(@Valid @RequestBody ValidateEmailDTO request) {
        TokensDTO response = authService.validateEmail(request.code(), AuthService.getAuthenticationPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/email-code")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Resend email validation code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email verification code resent"),
            @ApiResponse(responseCode = "500", description = "Error sending email",
                content =  @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    public ResponseEntity<ResendEmailCodeResponseDTO> resendEmailCode() {
        emailCodeService.sendVerificationCode(AuthService.getAuthenticationPrincipal());
        return ResponseEntity.status(HttpStatus.OK).body(new ResendEmailCodeResponseDTO("Email code resent"));
    }
}
