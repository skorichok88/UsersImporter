package com.importer;

import com.importer.domain.User;
import com.importer.domain.UsersStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class UsersStorageTest {

    @Autowired
    private UsersStorage usersStorage;

    @Autowired
    private Storage storage;

    @Test
    public void saveUsers_ShouldSaveUsersWithoutErrors() throws IOException {
        Map<String, User> users = new HashMap<>();
        for (int i = 0; i < 6; i++) {
            String email = "useremail" + i + "@mail.com";
            User user = new User.Builder().email(email).lastAccessDate("11-11-198" + i).build();
            users.put(email, user);
        }

        usersStorage.saveUsers(users);

        Map<String, User> savedUsers = usersStorage.getAllUsersData();
        Assertions.assertEquals(users.size(), savedUsers.size());
        for (String userEmail : users.keySet()) {
            Assertions.assertNotNull(savedUsers.get(userEmail));
        }
    }

    @AfterEach
    public void cleanupStorage() {
        storage.cleanupUsersStorage();
    }
}
