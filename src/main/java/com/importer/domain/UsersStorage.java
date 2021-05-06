package com.importer.domain;

import com.importer.file.FileStorage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UsersStorage {

    private final FileStorage fileStorage;

    public UsersStorage(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }

    public synchronized void saveUsers(Map<String, User> users) throws IOException {
        if (users.isEmpty()) {
            return;
        }
        Object storageData = fileStorage.getStorageData();

        if (null == storageData) {
            fileStorage.saveDataToStorage(users);
        } else {
            if (!(storageData instanceof HashMap)) throw new IOException("File data has incorrect data structure.");

            Map<String, User> savedUsers = (Map<String, User>) storageData;

            savedUsers.putAll(users);
            fileStorage.saveDataToStorage(savedUsers);
        }
    }

    public Map<String, User> getAllUsersData() throws IOException {
        Object storageData = fileStorage.getStorageData();
        if (!(storageData instanceof HashMap)) throw new IOException("File data has incorrect data structure.");

        return (Map<String, User>) storageData;
    }
}
