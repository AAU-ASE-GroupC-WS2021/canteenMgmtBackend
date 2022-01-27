package edu.aau.groupc.canteenbackend.auth;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AuthTest {

    @Test
    void constructor1() {
        Auth a = new Auth("username");

        assertEquals("username", a.getUsername());
        assertTrue(a.getTimeEnd() > a.getTimeStart());
    }

    @Test
    void constructor2() {
        Auth a = new Auth("username", "token");

        assertEquals("username", a.getUsername());
        assertEquals("token", a.getToken());
        assertTrue(a.getTimeEnd() > a.getTimeStart());
    }

    @Test
    void testEquals_Same() {
        Auth a = new Auth("username", "token");

        assertEquals(a, a);
    }

    @Test
    void testEquals_Different() {
        Auth a = new Auth("username", "token");
        Auth b = new Auth("username1", "token");

        assertNotEquals(a, b);
    }

    @Test
    void testEquals_Null() {
        Auth a = new Auth("username", "token");

        assertNotEquals(a, null);
    }

    @Test
    void testHashCode() {
        Auth a = new Auth("username", "token");
        int hash = Objects.hash(a.getId(), a.getUsername(), a.getToken(), a.getTimeStart(), a.getTimeEnd());

        assertEquals(hash, a.hashCode());
    }

    @Test
    void testToString() {
        Auth a = new Auth("username", "token");

        String expected = "Auth{" +
                "id=" + a.getId() +
                ", username='" + a.getUsername() + '\'' +
                ", token='" + a.getToken() + '\'' +
                ", timeStart=" + a.getTimeStart() +
                ", timeEnd=" + a.getTimeEnd() +
                '}';

        assertEquals(expected, a.toString());
    }
}