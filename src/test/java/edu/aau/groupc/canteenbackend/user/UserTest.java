package edu.aau.groupc.canteenbackend.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser_testConstructor_returnsOK() {
        User a = new User("username", "password", User.Type.GUEST);

        assertEquals("username", a.getUsername());
        assertEquals("password", a.getPassword());
        assertEquals(User.Type.GUEST, a.getType());
    }

}