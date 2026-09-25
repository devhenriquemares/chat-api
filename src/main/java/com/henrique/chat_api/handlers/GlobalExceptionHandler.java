package com.henrique.chat_api.handlers;

import com.henrique.chat_api.dtos.error.ErrorResponseDTO;
import com.henrique.chat_api.dtos.error.FieldErrorDTO;
import com.henrique.chat_api.exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(Exception exception, int status, String code) {
        return this.buildErrorResponse(exception, status, null, code);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(Exception exception, int status, List<FieldErrorDTO> errors, String code) {
        return ResponseEntity.status(status).body(
                new ErrorResponseDTO(status, code, exception.getMessage(), errors, Instant.now())
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> emailAlreadyExistsExceptionHandler(EmailAlreadyExistsException exception) {
        return buildErrorResponse(exception, HttpStatus.CONFLICT.value(), "EMAIL_ALREADY_EXISTS");
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> resourceNotFoundExceptionHandler(ResourceNotFoundException exception) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND.value(), "RESOURCE_NOT_FOUND");
    }

    @ExceptionHandler(OldPasswordRequiredException.class)
    public ResponseEntity<ErrorResponseDTO> oldPasswordRequiredExceptionHandler(OldPasswordRequiredException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST.value(), "OLD_PASSWORD_REQUIRED");
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDTO> invalidPasswordExceptionHandler(InvalidPasswordException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST.value(), "INVALID_PASSWORD");
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ErrorResponseDTO> mailExceptionHandler(MailException exception) {
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR.value(), "MAIL_SENDING_ERROR");
    }

    @ExceptionHandler(InvalidEmailCodeException.class)
    public ResponseEntity<ErrorResponseDTO> invalidEmailCodeExceptionHandler(InvalidEmailCodeException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST.value(), "INVALID_EMAIL_CODE");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldErrorDTO> errors = exception.getAllErrors()
                .stream()
                .map(error -> new FieldErrorDTO(
                        ((FieldError) error).getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST.value(), errors, "INVALID_ARGUMENTS");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST.value(), "INVALID_REQUEST_BODY");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDTO> badCredentialsExceptionHandler(BadCredentialsException exception) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST.value(), "EMAIL_OR_PASSWORD_INVALID");
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDTO> jwtExceptionHandler(ExpiredJwtException exception) {
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED.value(), "INVALID_TOKEN");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> genericExceptionHandler(Exception exception) {
        log.error("Internal server error exception", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(
                new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR", "Something went wrong", null, Instant.now())
        );
    }
}
