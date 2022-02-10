package edu.aau.groupc.canteenbackend.auth.controllers;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.dto.LoginDto;
import edu.aau.groupc.canteenbackend.auth.security.AuthenticationInterceptor;
import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.controllers.UserController;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.json.JSONObject;
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
class LoginControllerTest {

    @Autowired
    IUserService userService;

    @Autowired
    IAuthService authService;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Value("${app.auth.header}")
    String authHeaderKey;

    private MockMvc mvc;

    @Test
    void login() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new LoginController(authService)).build();
        LoginDto login = new LoginDto("username", "a".repeat(64));
        User user = userService.create(new User(login.getUsername(), login.getPassword(), User.Type.USER));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .post("/api/auth/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(login.toJSONString()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(user, authService.getUserByToken(res.getResponse().getContentAsString()));
        assertEquals(1, authService.findAll().size());
    }

    @Test
    void logout() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new LoginController(authService)).build();
        User user = userService.create(new User("username", "a".repeat(64), User.Type.USER));
        Auth auth = authService.login("username", "a".repeat(64));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .delete("/api/auth/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(auth.getToken()))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(0, authService.findAll().size());
    }

    @Test
    void getUserSelfInfo() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new UserController(userService))
                .addInterceptors(authenticationInterceptor) // INTERCEPTOR IS MANDATORY !!!
                .build();
        User user = userService.create(new User("username", "a".repeat(64), User.Type.USER));
        Auth auth = authService.login("username", "a".repeat(64));

        HttpHeaders headers = new HttpHeaders();
        headers.put(authHeaderKey, List.of(auth.getToken()));

        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .get("/api/register")
                        .headers(headers))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject json = new JSONObject(res.getResponse().getContentAsString());

        assertEquals("username", json.getString("username"));
        assertEquals("USER", json.getString("type"));
    }

}
