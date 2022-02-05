package edu.aau.groupc.canteenbackend.auth.dto;

import edu.aau.groupc.canteenbackend.auth.Auth;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class LogoutDtoTest {

    @Test
    void constructor() {
        LogoutDto a = new LogoutDto("username", "token");

        assertEquals("username", a.getUsername());
        assertEquals("token", a.getToken());
    }

    @Test
    void setters() {
        LogoutDto a = new LogoutDto("", "");

        a.setUsername("username");
        a.setToken("token");

        assertEquals("username", a.getUsername());
        assertEquals("token", a.getToken());
    }

    @Test
    void toEntity() {
        LogoutDto a = new LogoutDto("username", "token");
        Auth auth = a.toEntity();

        assertEquals("username", auth.getUsername());
        assertEquals("token", auth.getToken());
    }

    @Test
    void testEquals_Same() {
        LogoutDto a = new LogoutDto("username", "token");

        assertEquals(a, a);
    }

    @Test
    void testEquals_Identical() {
        LogoutDto a = new LogoutDto("username", "token");
        LogoutDto b = new LogoutDto("username", "token");

        assertEquals(a, b);
    }

    @Test
    void testEquals_Different() {
        LogoutDto a = new LogoutDto("username", "token");
        LogoutDto b = new LogoutDto("username2", "token");

        assertNotEquals(a, b);
    }

    @Test
    void testEquals_Null() {
        LogoutDto a = new LogoutDto("username", "token");

        assertNotNull(a);
    }

    @Test
    void testHashCode() {
        LogoutDto a = new LogoutDto("username", "token");
        int hash = Objects.hash(a.getUsername(), a.getToken());

        assertEquals(hash, a.hashCode());
    }

    @Test
    void testToString() {
        LogoutDto a = new LogoutDto("username", "token");
        String expected = "LogoutDto {" +
                "username='" + a.getUsername() + '\'' +
                ", token='" + a.getToken() + '\'' +
                '}';

        assertEquals(expected, a.toString());
    }
}