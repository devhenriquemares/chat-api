package com.henrique.chat_api.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    private static String handleMessage(String resource) {
        StringBuilder message = new StringBuilder("Requested resource not found");
        if (resource != null) message.append(": ").append(resource);

        return message.toString();
    }

    public ResourceNotFoundException() {
        super(handleMessage(null));
    }

    public ResourceNotFoundException(String resource) {
        super(handleMessage(resource));
    }
}
