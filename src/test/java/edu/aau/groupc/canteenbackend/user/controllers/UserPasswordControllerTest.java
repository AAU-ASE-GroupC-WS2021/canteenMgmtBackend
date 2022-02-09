package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.dto.LoginDto;
import edu.aau.groupc.canteenbackend.auth.security.AuthenticationInterceptor;
import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserPasswordDTO;
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
class UserPasswordControllerTest {

    private MockMvc mvc;

    @Autowired
    IUserService userService;

    @Autowired
    IAuthService authService;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Value("${app.auth.header}")
    String authHeaderKey;

    @BeforeEach
    void setupUsers() {
        mvc = MockMvcBuilders.standaloneSetup(new UserPasswordController(userService))
                .addInterceptors(authenticationInterceptor)
                .build();
    }

    @Test
    void changePassword_OK() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO(login.getUsername(), login.getPassword(), "b".repeat(64));

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/password")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userPasswordDTO.toJSONString()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("b".repeat(64), userService.findById(user.getId()).get().getPassword());
    }

    @Test
    void changePassword_incorrectOldPassword() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO(login.getUsername(), "c".repeat(64), "b".repeat(64));

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/password")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userPasswordDTO.toJSONString()))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("Old password is not correct!", res.getResponse().getContentAsString());
    }

    @Test
    void changePassword_TryingUpdateSomeoneElsesPassword() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        Auth auth = authService.login(login.getUsername(), login.getPassword());
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO("wrongusername", login.getPassword(), "b".repeat(64));

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/password")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userPasswordDTO.toJSONString()))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertEquals("You can change password only for your account!", res.getResponse().getContentAsString());
    }

    @Test
    void changePassword_NoAuth() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        UserPasswordDTO userPasswordDTO = new UserPasswordDTO(login.getUsername(), login.getPassword(), "b".repeat(64));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userPasswordDTO.toJSONString()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertTrue(true);
    }

    @Test
    void changePassword_noBody() throws Exception {
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));
        Auth auth = authService.login(login.getUsername(), login.getPassword());

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/password")
                        .headers(headers))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(true);
    }
}