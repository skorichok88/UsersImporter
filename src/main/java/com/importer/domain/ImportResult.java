package com.importer.domain;

import java.util.HashMap;
import java.util.Map;

public class ImportResult {
    private int successfullyImportedUsers;
    private final Map<String, String> usersWithErrors;


    public ImportResult() {
        this.usersWithErrors = new HashMap<>();
    }

    public int getSuccessfullyImportedUsers() {
        return successfullyImportedUsers;
    }

    public Map<String, String> getUsersWithErrors() {
        return usersWithErrors;
    }

    public void setSuccessfullyImportedUsers(int successfullyImportedUsers) {
        this.successfullyImportedUsers = successfullyImportedUsers;
    }

    public void addUserError(String userEmail, String errorMessage) {
        usersWithErrors.put(userEmail, errorMessage);
    }
}
