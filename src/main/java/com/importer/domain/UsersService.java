package com.importer.domain;

import com.importer.exception.IncorrectColumnsSizeException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsersService {

    private final UsersStorage usersStorage;

    public UsersService(UsersStorage usersStorage) {
        this.usersStorage = usersStorage;
    }

    //TODO Abstractions are leaking due to exceptions;
    public ImportResult importUsers(List<List<String>> usersData) throws IOException {
        ImportResult importResult = new ImportResult();
        Map<String, User> users = new HashMap<>();
        int userNumber = 1;
        for (List<String> columns : usersData) {
            User user = null;
            try {
                user = buildUser(columns);
                if (users.containsKey(user.getEmail())) {
                    users.remove(user.getEmail());
                    importResult.addUserError("user_" + userNumber, "Duplicated user email " + user.getEmail());
                } else {
                    users.put(user.getEmail(), user);
                }
            } catch (Exception e) {
                addFailedUserToImportResult(importResult, userNumber, e);
            }
            userNumber++;
        }

        usersStorage.saveUsers(users);
        importResult.setSuccessfullyImportedUsers(users.size());
        return importResult;
    }

    private void addFailedUserToImportResult(ImportResult importResult, int userNumber, Exception e) {
        String user = "user_" + userNumber;
        importResult.addUserError(user, e.getClass().getName() + " " + e.getMessage());
    }

    public Map<String, User> getAllUsers() throws IOException {
        return usersStorage.getAllUsersData();
    }

    private User buildUser(List<String> columns) {
        if (columns.size() < 6) {
            throw new IncorrectColumnsSizeException(6, columns.size());
        }
        return new User.Builder()
                .email(columns.get(0))
                .lastname(columns.get(1))
                .firstname(columns.get(2))
                .fiscalCode(columns.get(3))
                .description(columns.get(4))
                .lastAccessDate(columns.get(5))
                .build();
    }
}
