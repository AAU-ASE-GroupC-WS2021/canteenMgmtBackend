package edu.aau.groupc.canteenbackend.auth.security;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import edu.aau.groupc.canteenbackend.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("H2Database")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationInterceptorIntegrationTest extends AbstractControllerTest {
    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthService authService;

    @Value("${app.auth.header}")
    private String tokenHeader;

    private Map<User.Type, Auth> tokens;

    private MockMvc mvc;

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @BeforeAll
    void setupUsersAndAuth() {
        mvc = MockMvcBuilders.standaloneSetup(new AuthTestController())
                .addInterceptors(authenticationInterceptor).build();

        User user = new User("someUser", "somePassword", User.Type.USER);
        User admin = new User("someAdmin", "somePassword", User.Type.ADMIN);
        User owner = new User("someOwner", "somePassword", User.Type.OWNER);

        userService.create(user);
        userService.create(admin);
        userService.create(owner);

        tokens = new HashMap<>();
        tokens.put(User.Type.USER, authService.login(user.getUsername(), user.getPassword()));
        tokens.put(User.Type.ADMIN, authService.login(admin.getUsername(), admin.getPassword()));
        tokens.put(User.Type.OWNER, authService.login(owner.getUsername(), owner.getPassword()));
    }

    private HttpHeaders getTokenHeader(User.Type userType) {
        try {
            return getTokenHeader(tokens.get(userType).getToken());
        } catch (RuntimeException e) {
            return new HttpHeaders();
        }
    }

    private HttpHeaders getTokenHeader(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.put(tokenHeader, List.of(token));
            return headers;
        } catch (RuntimeException e) {
            return new HttpHeaders();
        }
    }

    @Test
    void testUnsecured_NotAuthenticated_ThenOK() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/authtest/unsecured")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/authtest/unsecured", "/authtest/securedNoType",
            "/authtest/securedGuest", "/authtest/securedUser"})
    void testUser_ThenOK(String path) throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get(path)
                        .headers(getTokenHeader(User.Type.USER))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/authtest/securedNoType", "/authtest/securedUser",
            "/authtest/securedAdmin", "/authtest/securedOwner"})
    void testNotAuthenticated_ThenUnauthorized(String path) throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get(path)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testSecuredGuest_NotAuthenticated_ThenOK() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/authtest/securedGuest")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSecuredUser_Admin_ThenOK() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/authtest/securedUser")
                        .headers(getTokenHeader(User.Type.ADMIN))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/authtest/securedUser", "/authtest/securedAdmin", "/authtest/securedOwner"})
    void testOwner_ThenOK(String path) throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get(path)
                        .headers(getTokenHeader(User.Type.OWNER))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/authtest/securedAdmin", "/authtest/securedOwner"})
    void testUser_ThenForbidden(String path) throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get(path)
                        .headers(getTokenHeader(User.Type.USER))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void testSecuredAdmin_Admin_ThenOK() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/authtest/securedAdmin")
                        .headers(getTokenHeader(User.Type.ADMIN))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSecuredOwner_Admin_ThenForbidden() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/authtest/securedOwner")
                        .headers(getTokenHeader(User.Type.ADMIN))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void testSecured_InvalidToken_ThenUnauthorized() throws Exception {
        mvc.perform( MockMvcRequestBuilders
                        .get("/authtest/securedUser")
                        .headers(getTokenHeader("someInvalidToken"))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Check if the user is correctly retrievable within the controller method.
     * (Returning it via response is just for test simplification)
     * @throws JSONException If response body cannot be parsed.
     */
    @Test
    void testSecured_GetUserDataFromRequest() throws Exception {
        MvcResult res = mvc.perform( MockMvcRequestBuilders
                        .get("/authtest/securedUserReturnUserData")
                        .headers(getTokenHeader(User.Type.USER))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        JSONObject responseJson = new JSONObject(res.getResponse().getContentAsString());
        assertEquals(tokens.get(User.Type.USER).getUsername(), responseJson.getString("username"));
    }
}
