package edu.aau.groupc.canteenbackend.avatar.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("H2Database")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AvatarServiceTest {

    @Autowired
    IAvatarService avatarService;

    @Test
    void findAll_BeforeAdding() {
        assertEquals(0, avatarService.findAll().size());
    }

    @Test
    void findAll_AfterAddingOne() {
        avatarService.updateOrAddAvatar("username", "avatar");

        assertEquals(1, avatarService.findAll().size());
    }

    @Test
    void findAll_AfterAddingOneThenUpdating() {
        avatarService.updateOrAddAvatar("username", "avatar");
        avatarService.updateOrAddAvatar("username", "avatar");

        assertEquals(1, avatarService.findAll().size());
    }

    @Test
    void findAll_AfterAddingOneThenDeleting() {
        avatarService.updateOrAddAvatar("username", "avatar");
        avatarService.deleteAvatar("username");

        assertEquals(0, avatarService.findAll().size());
    }

    @Test
    void getAvatar_NotExist() {
        assertNull(avatarService.getAvatar("username"));
    }

    @Test
    void getAvatar_Ok() {
        // Avatar string must be a valid Base64 string!
        avatarService.updateOrAddAvatar("username", "YXZhdGFy");

        assertNotNull(avatarService.getAvatar("username"));
        assertEquals("YXZhdGFy", avatarService.getAvatar("username").getAvatar());
        assertEquals("username", avatarService.getAvatar("username").getUsername());
    }
}