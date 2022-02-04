package edu.aau.groupc.canteenbackend.auth.security;

import edu.aau.groupc.canteenbackend.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationInterceptorTest {

    private AuthenticationInterceptor authInterceptor = new AuthenticationInterceptor();

    @Test
    void requiredRole_NullArgument_ThenGuest() {
        assertEquals(User.Type.GUEST, authInterceptor.getRequiredUserRole(null));
    }

    @Test
    void isAuthorized_requiredNull_ThenFalse() {
        assertFalse(authInterceptor.isAuthorized(null, User.Type.GUEST));
    }

    @Test
    void isAuthorized_userTypeNull_ThenFalse() {
        assertFalse(authInterceptor.isAuthorized(new User("", "", null), User.Type.GUEST));
    }

    @Test
    void isAuthorized_userTypeGuestRequiredGuest_ThenTrue() {
        assertTrue(authInterceptor.isAuthorized(new User("", "", User.Type.GUEST), User.Type.GUEST));
    }

    @Test
    void isAuthorized_userTypeGuestRequiredUser_ThenFalse() {
        assertFalse(authInterceptor.isAuthorized(new User("", "", User.Type.GUEST), User.Type.USER));
    }
}
