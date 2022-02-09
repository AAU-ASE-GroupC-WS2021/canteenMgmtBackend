package edu.aau.groupc.canteenbackend.avatar;

import edu.aau.groupc.canteenbackend.user.dto.UserPasswordDTO;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AvatarTest {

    @Test
    void testEquals_Same() {
        Avatar avatar = new Avatar("username", "avatar");

        assertEquals(avatar, avatar);
    }

    @Test
    void testEquals_Identical() {
        Avatar avatar1 = new Avatar("username", "avatar");
        Avatar avatar2 = new Avatar("username", "avatar");

        assertEquals(avatar1, avatar2);
    }

    @Test
    void testEquals_Different() {
        Avatar avatar1 = new Avatar("username1", "avatar");
        Avatar avatar2 = new Avatar("username2", "avatar");

        assertNotEquals(avatar1, avatar2);
    }

    @Test
    void testHashCode() {
        Avatar avatar = new Avatar("username", "YXZhdGFy");
        int hash = Objects.hash(avatar.getId(), avatar.getUsername(), avatar.getAvatar());

        assertEquals("YXZhdGFy", avatar.getAvatar());
        assertEquals(hash, avatar.hashCode());
    }
}