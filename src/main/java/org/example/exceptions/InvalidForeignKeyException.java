package org.example.exceptions;

public class InvalidForeignKeyException extends Exception {
    public InvalidForeignKeyException(String message) {
        super(message);
    }
}
