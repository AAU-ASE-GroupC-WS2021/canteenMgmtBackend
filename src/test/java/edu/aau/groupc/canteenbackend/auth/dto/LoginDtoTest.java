package edu.aau.groupc.canteenbackend.auth.dto;

import edu.aau.groupc.canteenbackend.auth.Auth;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LoginDtoTest {

    @Test
    void constructor() {
        LoginDto a = new LoginDto("username", "password");

        assertEquals("username", a.getUsername());
        assertEquals("password", a.getPassword());
    }

    @Test
    void setters() {
        LoginDto a = new LoginDto("", "");

        a.setUsername("username");
        a.setPassword("password");

        assertEquals("username", a.getUsername());
        assertEquals("password", a.getPassword());
    }

    @Test
    void toEntity() {
        LoginDto a = new LoginDto("username", "password");
        Auth auth = a.toEntity();

        assertEquals("username", auth.getUsername());
    }

    @Test
    void testEquals_Same() {
        LoginDto a = new LoginDto("username", "password");

        assertEquals(a, a);
    }

    @Test
    void testEquals_Identical() {
        LoginDto a = new LoginDto("username", "password");
        LoginDto b = new LoginDto("username", "password");

        assertEquals(a, b);
    }

    @Test
    void testEquals_Different() {
        LoginDto a = new LoginDto("username", "password");
        LoginDto b = new LoginDto("username2", "password");

        assertNotEquals(a, b);
    }

    @Test
    void testEquals_Null() {
        LoginDto a = new LoginDto("username", "password");

        assertNotNull(a);
    }

    @Test
    void testHashCode() {
        LoginDto a = new LoginDto("username", "password");
        int hash = Objects.hash(a.getUsername(), a.getPassword());

        assertEquals(hash, a.hashCode());
    }

    @Test
    void testToString() {
        LoginDto a = new LoginDto("username", "password");
        String expected = "LoginDto {" +
                "username='" + a.getUsername() + '\'' +
                ", password='" + a.getPassword() + '\'' +
                '}';

        assertEquals(expected, a.toString());
    }
}