package com.henrique.chat_api.controllers;

import com.henrique.chat_api.dtos.auth.LoggedUserResponseDTO;
import com.henrique.chat_api.dtos.auth.LoginUserDTO;
import com.henrique.chat_api.dtos.auth.ValidateEmailDTO;
import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.services.AuthService;
import com.henrique.chat_api.services.EmailCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailCodeService emailCodeService;

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

    @PostMapping("/email-code")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> validateEmail(@RequestBody ValidateEmailDTO request) {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String response = authService.validateEmail(request.code(), user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/email-code")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> resendEmailCode() {
        UserAccount user = (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        emailCodeService.sendVerificationCode(user);
        return ResponseEntity.status(HttpStatus.OK).body("Email code resented");
    }
}
