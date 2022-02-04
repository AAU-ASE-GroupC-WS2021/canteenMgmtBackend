package edu.aau.groupc.canteenbackend.user;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser_testConstructor_returnsOK() {
        User a = new User("username", "password", User.Type.GUEST);

        assertEquals("username", a.getUsername());
        assertEquals("password", a.getPassword());
        assertEquals(User.Type.GUEST, a.getType());
    }

    @Test
    void createUser_testSetters_returnsOK() {
        User a = new User("", "", User.Type.GUEST);

        a.setId(5L);
        a.setUsername("username");
        a.setPassword(("password"));
        a.setType(User.Type.USER);

        assertEquals(5L, a.getId());
        assertEquals("username", a.getUsername());
        assertEquals("password", a.getPassword());
        assertEquals(User.Type.USER, a.getType());
    }

    @Test
    void createUser_testEquals_returnsOK() {
        User a = new User("username", "password", User.Type.GUEST);
        User b = new User("username", "password", User.Type.GUEST);

        assertEquals(a, b);
    }

    @Test
    void createUser_testEqualsSame_returnsOK() {
        User a = new User("username", "password", User.Type.GUEST);

        assertEquals(a, a);
    }

    @Test
    void createUser_testNotEquals_returnsOK() {
        User a = new User("username", "password", User.Type.GUEST);
        User b = new User("username", "password", User.Type.USER);

        assertNotEquals(a, b);
    }

    @Test
    void createUser_testNotEqualsNull_returnsOK() {
        User a = new User("username", "password", User.Type.GUEST);

        assertNotNull(a);
    }

    @Test
    void createUser_testHash_returnsOK() {
        User a = new User("username", "password", User.Type.GUEST);
        int expectedHash = Objects.hash(a.getId(), a.getUsername(), a.getPassword(), a.getType());

        assertEquals(expectedHash, a.hashCode());
    }

    @Test
    void getUserSelfInfo_GUEST() throws JSONException {
        User a = new User("username", "password", User.Type.GUEST);
        JSONObject userJson = new JSONObject(a.getUserSelfInfo());

        assertEquals("username", userJson.getString("username"));
        assertEquals("GUEST", userJson.getString("type"));
    }

    @Test
    void getUserSelfInfo_USER() throws JSONException {
        User a = new User("username", "password", User.Type.USER);
        JSONObject userJson = new JSONObject(a.getUserSelfInfo());

        assertEquals("username", userJson.getString("username"));
        assertEquals("USER", userJson.getString("type"));
    }

    @Test
    void getUserSelfInfo_ADMIN() throws JSONException {
        User a = new User("username", "password", User.Type.ADMIN);
        JSONObject userJson = new JSONObject(a.getUserSelfInfo());

        assertEquals("username", userJson.getString("username"));
        assertEquals("ADMIN", userJson.getString("type"));
    }

    @Test
    void getUserSelfInfo_OWNER() throws JSONException {
        User a = new User("username", "password", User.Type.OWNER);
        JSONObject userJson = new JSONObject(a.getUserSelfInfo());

        assertEquals("username", userJson.getString("username"));
        assertEquals("OWNER", userJson.getString("type"));
    }
}