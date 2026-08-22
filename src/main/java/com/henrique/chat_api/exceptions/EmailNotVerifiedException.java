package com.henrique.chat_api.exceptions;

public class EmailNotVerifiedException extends RuntimeException {
    public EmailNotVerifiedException() {
        super("User's email not verified");
    }
}
