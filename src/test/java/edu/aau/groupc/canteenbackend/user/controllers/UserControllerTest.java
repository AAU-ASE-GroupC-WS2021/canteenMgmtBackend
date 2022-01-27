package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("H2Database")
class UserControllerTest extends AbstractControllerTest {

    @Autowired
    IUserService userService;

    @Test
    void userPOST_createNewUserUsernameNull_throwsException() {
        String body = "{ \"password\": \"" + "a".repeat(64) + "\" }";

        HttpClientErrorException thrown = assertThrows(
                HttpClientErrorException.class,
                () -> { makePostRequest("/api/register", body); },
                "Expected exception to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Username is required"));
    }

    @Test
    void userPOST_createNewUserUsernameTooShort_throwsException() {
        String body = "{ \"username\": \"AB\", \"password\": \"" + "a".repeat(64) + "\" }";

        HttpClientErrorException thrown = assertThrows(
                HttpClientErrorException.class,
                () -> { makePostRequest("/api/register", body); },
                "Expected exception to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Username must be at least 3 characters long!"));
    }

    @Test
    void userPOST_createNewUserUsernameTooLong_throwsException() {
        String body = "{ \"username\": \"" + "A".repeat(25) + "\", \"password\": \"" + "a".repeat(64) + "\" }";

        HttpClientErrorException thrown = assertThrows(
                HttpClientErrorException.class,
                () -> { makePostRequest("/api/register", body); },
                "Expected exception to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Username cannot be longer than 24 characters!"));
    }

    @Test
    void userPOST_createNewUserUsernameContainsForbiddenCharacter_throwsException() {
        String body = "{ \"username\": \"ABC$\", \"password\": \"" + "a".repeat(64) + "\" }";

        HttpClientErrorException thrown = assertThrows(
                HttpClientErrorException.class,
                () -> { makePostRequest("/api/register", body); },
                "Expected exception to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Username can contain only alphanumeric characters!"));
    }

    @Test
    void userPOST_createNewUserPasswordTooShort_throwsException() {
        String body = "{ \"username\": \"TestUser124\", \"password\": \"" + "a".repeat(63) + "\" }";

        HttpClientErrorException thrown = assertThrows(
                HttpClientErrorException.class,
                () -> { makePostRequest("/api/register", body); },
                "Expected exception to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Password must be exactly 64 characters long!"));
    }

    @Test
    void userPOST_createNewUserPasswordTooLong_throwsException() {
        String body = "{ \"username\": \"TestUser124\", \"password\": \"" + "a".repeat(65) + "\" }";

        HttpClientErrorException thrown = assertThrows(
                HttpClientErrorException.class,
                () -> { makePostRequest("/api/register", body); },
                "Expected exception to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Password must be exactly 64 characters long!"));
    }

    @Test
    void userPOST_createNewUserPasswordNull_throwsException() {
        String body = "{ \"username\": \"TestUser124\" }";

        HttpClientErrorException thrown = assertThrows(
                HttpClientErrorException.class,
                () -> { makePostRequest("/api/register", body); },
                "Expected exception to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Password is required"));
    }

    @Test
    void userPOST_createNewUserPasswordContainsInvalidChar_throwsException() {
        String body = "{ \"username\": \"TestUser124\", \"password\": \"" + "a".repeat(63) + "Z\" }";

        HttpClientErrorException thrown = assertThrows(
                HttpClientErrorException.class,
                () -> { makePostRequest("/api/register", body); },
                "Expected exception to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("Password can contain only hexadecimal digits!"));
    }

}
