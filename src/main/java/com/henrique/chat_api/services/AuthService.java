package com.henrique.chat_api.services;

import com.henrique.chat_api.dtos.TokensDTO;
import com.henrique.chat_api.dtos.auth.LoggedUserResponseDTO;
import com.henrique.chat_api.dtos.auth.LoginUserDTO;
import com.henrique.chat_api.dtos.user.CreateLocalUserDTO;
import com.henrique.chat_api.dtos.user.UserResponseDTO;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final EmailCodeService emailCodeService;

    public LoggedUserResponseDTO register(CreateLocalUserDTO request) {
        UserAccount user = userService.store(request);
        TokensDTO tokens = jwtService.generateTokens(user);
        emailCodeService.sendVerificationCode(user);

        return new LoggedUserResponseDTO(tokens, UserMapper.toResponse(user));
    }

    public LoggedUserResponseDTO login(LoginUserDTO request) {
        Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthentication);
        UserAccount user = (UserAccount) authentication.getPrincipal();
        TokensDTO tokens = jwtService.generateTokens(user);

        return new LoggedUserResponseDTO(tokens, UserMapper.toResponse(user));
    }

    public String validateEmail(String code, UserAccount user) {
        return emailCodeService.validate(code, user);
    }
}
