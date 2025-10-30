package org.example.exceptions;

public class InvalidForeignKeyException extends RuntimeException {
    public InvalidForeignKeyException(String message) {
        super(message);
    }
}
