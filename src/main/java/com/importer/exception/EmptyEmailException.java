package com.importer.exception;

public class EmptyEmailException extends RuntimeException {
    public EmptyEmailException() {
        super("User email field can't be empty or null");
    }
}
