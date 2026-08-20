package com.henrique.chat_api.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID userID) {
        super("No user found with the id " + userID.toString());
    }
}
