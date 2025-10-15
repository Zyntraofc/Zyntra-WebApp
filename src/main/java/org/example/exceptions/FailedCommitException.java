package org.example.exceptions;

public class FailedCommitException extends RuntimeException {
    public FailedCommitException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
