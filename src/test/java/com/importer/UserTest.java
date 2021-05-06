package com.importer;

import com.importer.domain.User;
import com.importer.exception.EmptyEmailException;
import com.importer.exception.IncorrectDateFormatException;
import com.importer.exception.IncorrectEmailFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserTest {
    public static Object[][] supportedDateFormats() {
        return new Object[][]{
                {"dd-MM-yyyy", "20-11-1989"},
                {"yyyy-MM-dd", "1989-11-20"},
                {"MM/dd/yyyy", "11/20/1989"},
                {"yyyy/MM/dd", "1988/02/23"},
                {"dd MM yyyy", "23 12 1990"},
        };
    }

    public static Object[][] supportedDateTimeFormats() {
        return new Object[][]{
                {"dd-MM-yyyy HH:mm", "20-11-1989 12:34"},
                {"yyyy-MM-dd HH:mm", "1989-11-20 13:45"},
                {"MM/dd/yyyy HH:mm", "11/20/1989 14:12"},
                {"yyyy/MM/dd HH:mm", "1988/02/23 23:23"}
        };
    }

    public static Object[][] incorrectEmailFormats() {
        return new Object[][]{
                {"user1#mail.com"},
                {"userEmail@gmail"},
                {"userEmail%.com"},
                {"#@mail.com"},
        };
    }

    @Test
    public void userBuild_shouldThrowExceptionIfEmailIsEmpty() {
        Assertions.assertThrows(
                EmptyEmailException.class,
                () -> new User.Builder().email("").firstname("firstName").build());
    }

    @Test
    public void userBuild_ShouldNotThrowExceptionIfAllFieldEmptyExceptEmail() {
        new User.Builder().email("userEmail@mail.com").build();
    }

    @ParameterizedTest
    @MethodSource("supportedDateTimeFormats")
    public void userBuild_ShouldCorrectlyParseDateTime(String dateFormat, String date) {
        User user = new User.Builder().email("userEmail@mail.com").lastAccessDate(date).build();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime expectedDate = LocalDateTime.parse(date, formatter);

        Assertions.assertEquals(expectedDate, user.getLastAccessDate());
    }

    @ParameterizedTest
    @MethodSource("supportedDateFormats")
    public void userBuild_ShouldParseDateAsLocalDateTime(String dateFormat, String date) {
        User user = new User.Builder().email("userEmail@mail.com").lastAccessDate(date).build();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(dateFormat);
        LocalDateTime expectedDate = LocalDate.parse(date, formatter).atStartOfDay();

        Assertions.assertEquals(expectedDate, user.getLastAccessDate());
    }

    @ParameterizedTest
    @MethodSource("incorrectEmailFormats")
    public void userBuild_ShouldThrowExceptionIfEmailFormatIsIncorrect(String incorrectEmail) {
        Assertions.assertThrows(
                IncorrectEmailFormatException.class,
                () -> new User.Builder().email(incorrectEmail).build());
    }

    @Test
    public void userBuild_ShouldThrowExceptionIfDateFormatIsIncorrect() {
        Assertions.assertThrows(
                IncorrectDateFormatException.class,
                () -> new User.Builder().email("userEmail@mail.com").lastAccessDate("12#12#1989").build());

    }
}
