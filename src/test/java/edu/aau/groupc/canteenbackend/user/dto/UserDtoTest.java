package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {

    @Test
    void createUserDto_createUserDto_returnsUser() {
        UserDto userDto = new UserDto("username", "password");

        assertEquals("username", userDto.getUsername());
        assertEquals("password", userDto.getPassword());
    }

    @Test
    void toEntity_createUserDto_returnsUser() {
        UserDto userDto = new UserDto("username", "password");

        assertEquals("username", userDto.toEntity().getUsername());
        assertEquals("password", userDto.toEntity().getPassword());
        assertEquals(User.Type.USER, userDto.toEntity().getType());
    }

    @Test
    void createUserDto_testEqualsSame_returnsTrue() {
        UserDto userDto = new UserDto("username", "password");
        assertEquals(userDto, userDto);
    }

    @Test
    void createUserDto_testEqualsIdentical_returnsTrue() {
        UserDto userDto1 = new UserDto("username", "password");
        UserDto userDto2 = new UserDto("username", "password");
        assertEquals(userDto1, userDto2);
    }

    @Test
    void createUserDto_testNotEqualsNull_returnsTrue() {
        UserDto userDto = new UserDto("username", "password");
        assertNotNull(userDto);
    }

    @Test
    void createUserDto_testNotEqualsIdentical_returnsTrue() {
        UserDto userDto1 = new UserDto("username1", "password");
        UserDto userDto2 = new UserDto("username2", "password");
        assertNotEquals(userDto1, userDto2);
    }

    @Test
    void createUserDto_testHash_returnsOK() {
        UserDto a = new UserDto("username", "password");
        int expectedHash = Objects.hash(a.getUsername(), a.getPassword());

        assertEquals(expectedHash, a.hashCode());
    }

    @Test
    void UserDto_toString_returnsCorrect() {
        UserDto a = new UserDto("username", "password");

        String expected = "UserDto {" +
                "username='" + a.getUsername() + '\'' +
                ", password='" + a.getPassword() + '\'' +
                '}';

        assertEquals(expected, a.toString());
    }

}