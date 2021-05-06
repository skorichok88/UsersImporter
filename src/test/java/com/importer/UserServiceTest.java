package com.importer;

import com.importer.domain.ImportResult;
import com.importer.domain.UsersService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UsersService usersService;

    @Test
    public void importUsers_ShouldNotFail() throws IOException {
        List<List<String>> users = getCorrectUsersData();

        usersService.importUsers(users);
    }

    @Test
    public void importUsers_ShouldSuccessfullyImportUsers() throws IOException {
        List<List<String>> users = getCorrectUsersData();

        ImportResult importResult = usersService.importUsers(users);
        Assertions.assertEquals(users.size(), importResult.getSuccessfullyImportedUsers());
    }

    @Test
    public void importUsers_ShouldNotFailButSkipFailedUsers() throws IOException {
        List<List<String>> users = getIncorrectUsersData();

        ImportResult importResult = usersService.importUsers(users);
        Assertions.assertEquals(users.size(), importResult.getUsersWithErrors().size());
        Assertions.assertEquals(0, importResult.getSuccessfullyImportedUsers());
    }

    @Test
    public void importUsers_ShouldSkipUserWithIncorrectEmailFormat() throws IOException {
        List<List<String>> users = getCorrectUsersData();
        List<String> wrongUserColumns = new ArrayList<>();
        String incorrectUserEmail = "user@mailcom";
        wrongUserColumns.add(incorrectUserEmail);
        wrongUserColumns.add("userLastName");
        wrongUserColumns.add("userFirstName");
        wrongUserColumns.add("userCode");
        wrongUserColumns.add("userDescription");
        wrongUserColumns.add("12-12-1981");
        users.add(wrongUserColumns);

        ImportResult importResult = usersService.importUsers(users);
        Assertions.assertEquals(1, importResult.getUsersWithErrors().size());
        Assertions.assertNotNull(importResult.getUsersWithErrors().get("user_7"));
        Assertions.assertEquals(users.size() - 1, importResult.getSuccessfullyImportedUsers());
    }

    @Test
    public void importUsers_ShouldSkipUsersWithIncorrectColumnNumber() throws IOException {
        List<List<String>> users = getCorrectUsersData();
        List<String> wrongUserColumns = new ArrayList<>();
        String incorrectUserEmail = "user@mail.com";
        wrongUserColumns.add(incorrectUserEmail);
        wrongUserColumns.add("userLastName");
        wrongUserColumns.add("userFirstName");
        wrongUserColumns.add("userCode");
        wrongUserColumns.add("12-12-1981");
        users.add(wrongUserColumns);

        ImportResult importResult = usersService.importUsers(users);
        Assertions.assertEquals(1, importResult.getUsersWithErrors().size());
        Assertions.assertNotNull(importResult.getUsersWithErrors().get("user_7"));
        Assertions.assertEquals(users.size() - 1, importResult.getSuccessfullyImportedUsers());
    }

    @Test
    public void importUsers_ShouldSkipUserWithEmptyEmail() throws IOException {
        List<List<String>> users = getCorrectUsersData();
        List<String> wrongUserColumns = new ArrayList<>();
        String incorrectUserEmail = "";
        wrongUserColumns.add(incorrectUserEmail);
        wrongUserColumns.add("userLastName");
        wrongUserColumns.add("userFirstName");
        wrongUserColumns.add("userCode");
        wrongUserColumns.add("userDescription");
        wrongUserColumns.add("12-12-1981");
        users.add(wrongUserColumns);

        ImportResult importResult = usersService.importUsers(users);
        Assertions.assertEquals(1, importResult.getUsersWithErrors().size());
        Assertions.assertNotNull(importResult.getUsersWithErrors().get("user_7"));
        Assertions.assertEquals(users.size() - 1, importResult.getSuccessfullyImportedUsers());
    }

    @Test
    public void importUsers_ShouldSkipUserWithIncorrectLastAccessDate() throws IOException {
        List<List<String>> users = getCorrectUsersData();
        List<String> wrongUserColumns = new ArrayList<>();
        String userEmail = "user@mail.com";
        wrongUserColumns.add(userEmail);
        wrongUserColumns.add("userLastName");
        wrongUserColumns.add("userFirstName");
        wrongUserColumns.add("userCode");
        wrongUserColumns.add("userDescription");
        //Incorrect date format
        wrongUserColumns.add("12-12-19811");
        users.add(wrongUserColumns);

        ImportResult importResult = usersService.importUsers(users);
        Assertions.assertEquals(1, importResult.getUsersWithErrors().size());
        Assertions.assertNotNull(importResult.getUsersWithErrors().get("user_7"));
        Assertions.assertEquals(users.size() - 1, importResult.getSuccessfullyImportedUsers());
    }




    private List<List<String>> getCorrectUsersData() {
        List<List<String>> users = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<String> userColumns = new ArrayList<>();
            userColumns.add("user" + i + "@mail.com");
            userColumns.add("userLastName" + i);
            userColumns.add("userFirstName" + i);
            userColumns.add("userCode" + i);
            userColumns.add("userDescription" + i);
            userColumns.add("12-12-198" + i);
            users.add(userColumns);
        }
        return users;
    }

    private List<List<String>> getIncorrectUsersData() {
        List<List<String>> users = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            List<String> userColumns = new ArrayList<>();
            userColumns.add("user" + i + "@mail.com");
            userColumns.add("userLastName" + i);
            userColumns.add("userFirstName" + i);
            userColumns.add("userCode" + i);
            userColumns.add("userDescription" + i);
            //Incorrect date format
            userColumns.add("33-33-198" + i);
            users.add(userColumns);
        }
        return users;
    }
}
