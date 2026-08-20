package com.henrique.chat_api.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() { super("Invalid password"); }
}
