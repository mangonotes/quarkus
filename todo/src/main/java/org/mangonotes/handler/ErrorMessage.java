package org.mangonotes.handler;

public class ErrorMessage {
    private final String message;
    private final ErrorType type;

    public ErrorMessage(String message, ErrorType type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public ErrorType getType() {
        return type;
    }
}
