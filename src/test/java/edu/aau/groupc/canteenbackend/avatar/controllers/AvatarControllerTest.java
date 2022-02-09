package edu.aau.groupc.canteenbackend.avatar.controllers;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.dto.LoginDto;
import edu.aau.groupc.canteenbackend.auth.security.AuthenticationInterceptor;
import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import edu.aau.groupc.canteenbackend.avatar.dto.AvatarDto;
import edu.aau.groupc.canteenbackend.avatar.services.IAvatarService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.controllers.UserPasswordController;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("H2Database")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
class AvatarControllerTest {

    private MockMvc mvc;

    @Autowired
    IUserService userService;

    @Autowired
    IAuthService authService;

    @Autowired
    IAvatarService avatarService;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Value("${app.auth.header}")
    String authHeaderKey;

    @BeforeEach
    void setupUsers() {
        mvc = MockMvcBuilders.standaloneSetup(new AvatarController(avatarService))
                .addInterceptors(authenticationInterceptor)
                .build();
    }

    @Test
    void updateOrAddAvatar() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        AvatarDto avatarDto = new AvatarDto(login.getUsername(), "YXZhdGFy");

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/avatar")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(avatarDto.toJSONString()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("YXZhdGFy", avatarService.getAvatar(login.getUsername()).getAvatar());
    }

    @Test
    void updateOrAddAvatarByAdmin() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.ADMIN));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        AvatarDto avatarDto = new AvatarDto("SomeoneElsesUsername", "YXZhdGFy");

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/avatar")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(avatarDto.toJSONString()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("YXZhdGFy", avatarService.getAvatar("SomeoneElsesUsername").getAvatar());
    }

    @Test
    void updateOrAddAvatarByOtherUser() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        AvatarDto avatarDto = new AvatarDto("SomeoneElsesUsername", "YXZhdGFy");

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/avatar")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(avatarDto.toJSONString()))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(0, avatarService.findAll().size());
    }

    @Test
    void removeAvatar_OK() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        AvatarDto avatarDto = new AvatarDto(login.getUsername(), "YXZhdGFy");
        avatarService.updateOrAddAvatar(avatarDto.getUsername(), avatarDto.getAvatar());

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .delete("/api/avatar")
                        .headers(headers)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(login.getUsername()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(0, avatarService.findAll().size());
    }

    @Test
    void removeAvatar_ByAdmin() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.ADMIN));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        AvatarDto avatarDto = new AvatarDto("SomeoneElsesUsername", "YXZhdGFy");
        avatarService.updateOrAddAvatar(avatarDto.getUsername(), avatarDto.getAvatar());

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .delete("/api/avatar")
                        .headers(headers)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(avatarDto.getUsername()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(0, avatarService.findAll().size());
    }

    @Test
    void removeAvatar_ByOtherUser() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        AvatarDto avatarDto = new AvatarDto("SomeoneElsesUsername", "YXZhdGFy");
        avatarService.updateOrAddAvatar(avatarDto.getUsername(), avatarDto.getAvatar());

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .delete("/api/avatar")
                        .headers(headers)
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(avatarDto.getUsername()))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals(1, avatarService.findAll().size());
    }

    @Test
    void getAvatar_getAvatar_OK() throws Exception {
        AvatarDto avatarDto = new AvatarDto("username", "YXZhdGFy");
        avatarService.updateOrAddAvatar(avatarDto.getUsername(), avatarDto.getAvatar());

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .get("/api/avatar?username=username")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(avatarDto.getUsername()))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(res.getResponse().getContentAsString().contains("\"" + avatarDto.getAvatar() + "\""));
        assertEquals(res.getResponse().getContentAsString(), new AvatarDto(avatarDto.getUsername(), avatarDto.getAvatar()).toJSONString().replace(" ", ""));
    }

    @Test
    void getAvatar_getNonExisting() throws Exception {
        AvatarDto avatarDto = new AvatarDto("username", "YXZhdGFy");
        avatarService.updateOrAddAvatar(avatarDto.getUsername(), avatarDto.getAvatar());

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .get("/api/avatar?username=username2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(avatarDto.getUsername()))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(res.getResponse().getContentAsString());

        assertEquals(res.getResponse().getContentAsString(), new AvatarDto().toJSONString().replace(" ", "").replace("\"null\"", "null"));
    }
}