package org.example.exceptions;

public class FailedConnectionException extends RuntimeException {
    public FailedConnectionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
