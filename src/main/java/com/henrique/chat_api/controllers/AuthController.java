package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.auth.LoggedUserResponseDTO;
import com.henrique.chat_api.dtos.auth.LoginUserDTO;
import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<LoggedUserResponseDTO> register(@Valid @RequestBody CreateLocalUserDTO request) {
        LoggedUserResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoggedUserResponseDTO> login(@Valid @RequestBody LoginUserDTO request) {
        LoggedUserResponseDTO response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/email-validate")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> validateEmail(@RequestBody String emailCode) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String response = authService.validateEmail(emailCode, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
