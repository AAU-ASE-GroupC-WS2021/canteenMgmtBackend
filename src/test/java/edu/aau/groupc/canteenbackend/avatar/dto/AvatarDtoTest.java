package edu.aau.groupc.canteenbackend.avatar.dto;

import edu.aau.groupc.canteenbackend.avatar.Avatar;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AvatarDtoTest {

    @Test
    void testEquals_Same() {
        AvatarDto avatarDto = new AvatarDto("username", "avatar");

        assertEquals(avatarDto, avatarDto);
    }

    @Test
    void testEquals_Identical() {
        AvatarDto avatarDto1 = new AvatarDto("username", "avatar");
        AvatarDto avatarDto2 = new AvatarDto("username", "avatar");

        assertEquals(avatarDto1, avatarDto2);
    }

    @Test
    void testEquals_Different() {
        AvatarDto avatarDto1 = new AvatarDto("username1", "avatar");
        AvatarDto avatarDto2 = new AvatarDto("username2", "avatar");

        assertNotEquals(avatarDto1, avatarDto2);
    }

    @Test
    void testHashCode() {
        AvatarDto avatarDto = new AvatarDto("username", "avatar");
        int hash = Objects.hash(avatarDto.getUsername(), avatarDto.getAvatar());

        assertEquals(hash, avatarDto.hashCode());
    }

    @Test
    void testToString() {
        AvatarDto avatarDto = new AvatarDto("username", "avatar");

        assertEquals(avatarDto.toString(), avatarDto.toJSONString());
    }

    @Test
    void testToJSONString() {
        AvatarDto avatarDto = new AvatarDto("username", "avatar");

        assertEquals("{ \"username\": \"" + avatarDto.getUsername() + "\", \"avatar\": \"" + avatarDto.getAvatar() + "\" }", avatarDto.toJSONString());
    }

    @Test
    void testToEntity() {
        AvatarDto avatarDto = new AvatarDto("username", "avatar");

        assertEquals(avatarDto.toEntity(), new Avatar(avatarDto.getUsername(), avatarDto.getAvatar()));
    }

    @Test
    void testGetSet() {
        AvatarDto avatarDto = new AvatarDto("username", "avatar");

        assertEquals("{ \"username\": \"" + avatarDto.getUsername() + "\", \"avatar\": \"" + avatarDto.getAvatar() + "\" }", avatarDto.toJSONString());
    }
}