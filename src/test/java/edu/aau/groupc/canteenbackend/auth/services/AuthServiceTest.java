package edu.aau.groupc.canteenbackend.auth.services;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("H2Database")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class AuthServiceTest {

    @Autowired
    IUserService userService;

    @Autowired
    IAuthService authService;

    @Test
    void login_userValid_returnsValidLogin() {
        User user = userService.create(new User("username", "password", User.Type.USER));

        Auth auth = authService.login("username", "password");

        assertEquals("username", auth.getUsername());
        assertTrue(authService.isValidLogin(auth.getToken()));
        assertEquals(user, authService.getUserByToken(auth.getToken()));
    }

    @Test
    void login_userPasswordInvalid_returnsInvalidLogin() {
        userService.create(new User("username", "password", User.Type.USER));

        Auth auth = authService.login("username", "incorrect");

        assertNull(auth);
    }

    @Test
    void login_userInvalid_returnsInvalidLogin() {
        userService.create(new User("username", "password", User.Type.USER));

        Auth auth = authService.login("invalid", "password");

        assertNull(auth);
    }

    @Test
    void logout_noLogin_invalidLogout() {
        boolean loggedOut = authService.logout("non existing token");

        assertFalse(loggedOut);
    }

    @Test
    void logout_validLogin_validLogout() {
        userService.create(new User("username", "password", User.Type.USER));
        Auth auth = authService.login("username", "password");

        boolean loggedOut = authService.logout(auth.getToken());

        assertTrue(loggedOut);
    }

    @Test
    void login_validLoginDoubleLogout_returnsInvalidLogoutSecondTime() {
        userService.create(new User("username", "password", User.Type.USER));
        Auth auth = authService.login("username", "password");

        boolean loggedOut1 = authService.logout(auth.getToken());
        boolean loggedOut2 = authService.logout(auth.getToken());

        assertTrue(loggedOut1);
        assertFalse(loggedOut2);
    }

    @Test
    void findAll_twoValidLogins_returnsTwoAuths() {
        userService.create(new User("username1", "password1", User.Type.USER));
        userService.create(new User("username2", "password2", User.Type.USER));

        authService.login("username1", "password1");
        authService.login("username2", "password2");

        List<Auth> logins = authService.findAll();

        assertEquals(2, logins.size());
    }

    @Test
    void findAll_twoValidLoginsThenBothLogout_returnsNoAuths() {
        userService.create(new User("username1", "password1", User.Type.USER));
        userService.create(new User("username2", "password2", User.Type.USER));

        Auth auth1 = authService.login("username1", "password1");
        Auth auth2 = authService.login("username2", "password2");

        authService.logout(auth1.getToken());
        authService.logout(auth2.getToken());

        List<Auth> logins = authService.findAll();

        assertEquals(0, logins.size());
    }

    @Test
    void findAll_twoValidLoginsThenSecondLogout_returnsFirstAuth() {
        userService.create(new User("username1", "password1", User.Type.USER));
        userService.create(new User("username2", "password2", User.Type.USER));

        Auth auth1 = authService.login("username1", "password1");
        Auth auth2 = authService.login("username2", "password2");

        authService.logout(auth2.getToken());

        List<Auth> logins = authService.findAll();

        assertEquals(1, logins.size());
        assertEquals(auth1, logins.get(0));
    }

    @Test
    void findAll_twoValidLoginsThenFirstLogout_returnsSecondAuth() {
        userService.create(new User("username1", "password1", User.Type.USER));
        userService.create(new User("username2", "password2", User.Type.USER));

        Auth auth1 = authService.login("username1", "password1");
        Auth auth2 = authService.login("username2", "password2");

        authService.logout(auth1.getToken());

        List<Auth> logins = authService.findAll();

        assertEquals(1, logins.size());
        assertEquals(auth2, logins.get(0));
    }

    @Test
    void isValidLogin_loggedIn_returnsValid() {
        User user = userService.create(new User("username", "password", User.Type.USER));
        Auth auth = authService.login("username", "password");

        boolean valid = authService.isValidLogin(auth.getToken());

        assertTrue(valid);
    }

    @Test
    void isValidLogin_loggedOut_returnsNotValid() {
        User user = userService.create(new User("username", "password", User.Type.USER));
        Auth auth = authService.login("username", "password");
        authService.logout(auth.getToken());

        boolean valid = authService.isValidLogin(auth.getToken());

        assertFalse(valid);
    }

    @Test
    void getUserByToken() {
        User user1Before = userService.create(new User("username1", "password1", User.Type.USER));
        User user2Before = userService.create(new User("username2", "password2", User.Type.USER));
        User user3Before = userService.create(new User("username3", "password3", User.Type.USER));

        Auth auth1 = authService.login("username1", "password1");
        Auth auth2 = authService.login("username2", "password2");
        Auth auth3 = authService.login("username3", "password3");

        User user1 = authService.getUserByToken(auth1.getToken());
        User user2 = authService.getUserByToken(auth2.getToken());
        User user3 = authService.getUserByToken(auth3.getToken());

        assertEquals(user1Before, user1);
        assertEquals(user2Before, user2);
        assertEquals(user3Before, user3);
    }
}