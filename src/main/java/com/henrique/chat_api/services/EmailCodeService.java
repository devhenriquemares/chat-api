package com.henrique.chat_api.services;

import com.henrique.chat_api.entities.EmailCode;
import com.henrique.chat_api.entities.UserAccount;
import com.henrique.chat_api.exceptions.InvalidEmailCodeException;
import com.henrique.chat_api.repositories.IEmailCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class EmailCodeService {
    @Value("${MAIL_USERNAME}")
    private String from;

    private final JavaMailSender mailSender;
    private final IEmailCodeRepository codeRepository;

    @Async
    public void sendVerificationCode(UserAccount user) {
        String verificationCode = generateCode(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(user.getEmail());
        message.setSubject("Chat API email verification code");
        message.setText("This is your email verification code, do not share: " + verificationCode);
    }

    public String validate(String verifyCode, UserAccount user) throws InvalidEmailCodeException {
        EmailCode code = codeRepository.findByCode(verifyCode).orElseThrow(() -> new InvalidEmailCodeException("Don't exists"));
        if (code.getCodeOwner().equals(user)) throw new InvalidEmailCodeException("Invalid code owner");

        code.setExpired(Instant.now().isAfter(code.getExpiresAt()));
        if (code.isExpired()) throw new InvalidEmailCodeException("Expired code");

        user.setVerified(true);
        code.setExpired(true);
        return "Code successfully validated";
    }

    private String generateCode(UserAccount user) {
        EmailCode code = new EmailCode();
        code.setCode(EmailCode.generateCode());
        code.setCodeOwner(user);
        codeRepository.save(code);

        return code.getCode();
    }
}
