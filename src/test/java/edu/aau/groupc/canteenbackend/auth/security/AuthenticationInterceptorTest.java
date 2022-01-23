package edu.aau.groupc.canteenbackend.auth.security;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import edu.aau.groupc.canteenbackend.endpoints.AbstractControllerTest;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("H2Database")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AuthenticationInterceptorTest extends AbstractControllerTest {
    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthService authService;

    @Value("${app.auth.header}")
    private String tokenHeader;

    private Map<User.Type, Auth> tokens;

    @BeforeAll
    void setupUsersAndAuth() {
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
    void testUnsecured_NotAuthenticated_ThenOK() {
        ResponseEntity<String> response = makeGetRequest("/authtest/unsecured");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/authtest/unsecured", "/authtest/securedNoType",
            "/authtest/securedGuest", "/authtest/securedUser"})
    void testUser_ThenOK(String path) {
        ResponseEntity<String> response = makeGetRequest(path, getTokenHeader(User.Type.USER));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/authtest/securedNoType", "/authtest/securedUser",
            "/authtest/securedAdmin", "/authtest/securedOwner"})
    void testNotAuthenticated_ThenUnauthorized(String path) {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
                makeGetRequest(path));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    @Test
    void testSecuredGuest_NotAuthenticated_ThenOK() {
        ResponseEntity<String> response = makeGetRequest("/authtest/securedGuest");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSecuredUser_Admin_ThenOK() {
        ResponseEntity<String> response = makeGetRequest("/authtest/securedUser", getTokenHeader(User.Type.ADMIN));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/authtest/securedUser", "/authtest/securedAdmin", "/authtest/securedOwner"})
    void testOwner_ThenOK(String path) {
        ResponseEntity<String> response = makeGetRequest(path, getTokenHeader(User.Type.OWNER));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/authtest/securedAdmin", "/authtest/securedOwner"})
    void testUser_ThenForbidden(String path) {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
                makeGetRequest(path, getTokenHeader(User.Type.USER)));
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
    }

    @Test
    void testSecuredAdmin_Admin_ThenOK() {
        ResponseEntity<String> response = makeGetRequest("/authtest/securedAdmin", getTokenHeader(User.Type.ADMIN));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testSecuredOwner_Admin_ThenForbidden() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
                makeGetRequest("/authtest/securedOwner", getTokenHeader(User.Type.ADMIN)));
        assertEquals(HttpStatus.FORBIDDEN, exception.getStatusCode());
    }

    @Test
    void testSecured_InvalidToken_ThenUnauthorized() {
        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
                makeGetRequest("/authtest/securedUser", getTokenHeader("someInvalidToken")));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
    }

    /**
     * Check if the user is correctly retrievable within the controller method.
     * (Returning it via response is just for test simplification)
     * @throws JSONException If response body cannot be parsed.
     */
    @Test
    void testSecured_GetUserDataFromRequest() throws JSONException {
        ResponseEntity<String> response = makeGetRequest("/authtest/securedUserReturnUserData",
                getTokenHeader(User.Type.USER));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONObject responseJson = new JSONObject(response.getBody());
        assertEquals(tokens.get(User.Type.USER).getUsername(), responseJson.getString("username"));
    }
}