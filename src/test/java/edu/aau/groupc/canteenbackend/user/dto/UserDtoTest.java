package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}