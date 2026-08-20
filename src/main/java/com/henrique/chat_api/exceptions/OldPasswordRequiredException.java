package com.henrique.chat_api.exceptions;

public class OldPasswordRequiredException extends RuntimeException {
    public OldPasswordRequiredException() {
        super("Old password required");
    }
}
