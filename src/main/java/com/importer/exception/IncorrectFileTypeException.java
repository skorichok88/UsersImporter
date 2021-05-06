package com.importer.exception;

public class IncorrectFileTypeException extends Exception {
    public IncorrectFileTypeException() {
        super("File has content type different from text/csv");
    }
}
