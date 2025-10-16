package org.example.exceptions;

public class RollbackException extends RuntimeException {
    public RollbackException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
