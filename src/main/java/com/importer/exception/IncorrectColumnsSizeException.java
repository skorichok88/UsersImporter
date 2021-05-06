package com.importer.exception;

public class IncorrectColumnsSizeException extends RuntimeException {
    public IncorrectColumnsSizeException(int expectedColumns, int actualColumns) {
        super("User data has incorrect columns number. Expected " + expectedColumns + ",but found " + actualColumns);
    }
}
