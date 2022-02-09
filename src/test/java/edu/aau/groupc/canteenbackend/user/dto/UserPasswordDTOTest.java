package edu.aau.groupc.canteenbackend.user.dto;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserPasswordDTOTest {

    @Test
    void testEquals_Same() {
        UserPasswordDTO passwordDTO = new UserPasswordDTO("username", "passwordOld", "passwordNew");

        assertEquals(passwordDTO, passwordDTO);
    }

    @Test
    void testEquals_Identical() {
        UserPasswordDTO passwordDTO1 = new UserPasswordDTO("username", "passwordOld", "passwordNew");
        UserPasswordDTO passwordDTO2 = new UserPasswordDTO("username", "passwordOld", "passwordNew");

        assertEquals(passwordDTO1, passwordDTO2);
    }

    @Test
    void testEquals_Different() {
        UserPasswordDTO passwordDTO1 = new UserPasswordDTO("username1", "passwordOld", "passwordNew");
        UserPasswordDTO passwordDTO2 = new UserPasswordDTO("username2", "passwordOld", "passwordNew");

        assertNotEquals(passwordDTO1, passwordDTO2);
    }

    @Test
    void testHashCode() {
        UserPasswordDTO passwordDTO = new UserPasswordDTO("username", "passwordOld", "passwordNew");
        int hash = Objects.hash(passwordDTO.getUsername(), passwordDTO.getPasswordOld(), passwordDTO.getPasswordNew());

        assertEquals(hash, passwordDTO.hashCode());
    }

    @Test
    void testToString() {
        UserPasswordDTO passwordDTO = new UserPasswordDTO("username", "passwordOld", "passwordNew");

        assertEquals(passwordDTO.toJSONString(), passwordDTO.toString());
    }
}