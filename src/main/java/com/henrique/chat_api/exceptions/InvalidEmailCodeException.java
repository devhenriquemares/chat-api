package com.henrique.chat_api.exceptions;

public class InvalidEmailCodeException extends RuntimeException {
    public InvalidEmailCodeException(String message) {
        super("Invalid email code: " + message);
    }
}
