package com.henrique.chat_api.handlers;

import com.henrique.chat_api.dtos.ErrorResponseDTO;
import com.henrique.chat_api.exceptions.*;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(RuntimeException exception, HttpStatus status, String code) {
        return ResponseEntity.status(status).body(
                new ErrorResponseDTO(status, code, exception.getMessage(), Instant.now())
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> emailAlreadyExistsExceptionHandler(EmailAlreadyExistsException exception) {
        return buildErrorResponse(exception, HttpStatus.CONFLICT, "EMAIL_ALREADY_EXISTS");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> userNotFoundExceptionHandler(UserNotFoundException exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND, "USER_NOT_FOUND");
    }

    @ExceptionHandler(OldPasswordRequiredException.class)
    public ResponseEntity<ErrorResponseDTO> oldPasswordRequiredExceptionHandler(OldPasswordRequiredException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, "OLD_PASSWORD_REQUIRED");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDTO> invalidPasswordExceptionHandler(InvalidPasswordException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, "INVALID_PASSWORD");
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponseDTO> mailExceptionHandler(MailException exception) {
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, "MAIL_SENDING_ERROR");
    }

    @ExceptionHandler(InvalidEmailCodeException.class)
    public ResponseEntity<ErrorResponseDTO> invalidEmailCodeExceptionHandler(InvalidEmailCodeException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, "INVALID_EMAIL_CODE");
    }

    @ExceptionHandler(ChatNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> ChatNotFoundExceptionHandler(ChatNotFoundException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, "CHAT_NOT_FOUND");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> genericExceptionHandler(Exception exception) {
        log.error("Internal server error exception", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "Something went wrong", Instant.now())
        );
    }
}
