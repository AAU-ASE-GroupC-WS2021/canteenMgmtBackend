package edu.aau.groupc.canteenbackend.auth.security;

import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.AnnotatedElement;
import java.util.Optional;

/**
 * Interceptor handling authentication
 */

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Value("${app.auth.header}")
    protected String tokenHeader;

    @Autowired
    protected IAuthService authService;

    /**
     * Authenticate user and check authorization.
     * @param request Request
     * @param response Response
     * @param handler Handler
     * @return True if we wish to continue, false if wish to abort.
     */
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        User.Type requiredUserType = User.Type.GUEST;

        if(handler instanceof HandlerMethod handlerMethod) {
            requiredUserType = getRequiredUserRole(handlerMethod.getMethod());
        }
        // Guests can perform actions without authentication
        if (requiredUserType != User.Type.GUEST) {
            Optional<String> token = getToken(request);
            if (token.isEmpty() || !authService.isValidLogin(token.get())) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
            User authenticatedUser = authService.getUserByToken(token.get());
            if (authenticatedUser == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
            if (!isAuthorized(authenticatedUser, requiredUserType)) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return false;
            }

            // set user as a request attribute which can be accessed in any secured controller (endpoint) method
            request.setAttribute("user", authenticatedUser);
        }

        return true;
    }

    /**
     * Check whether a user is authorized for a required user type following the hierarchy
     * OWNER > ADMIN > USER > GUEST
     * @param user User
     * @param requiredType Type the user must at least have.
     * @return True, if authorized.
     */
    boolean isAuthorized(User user, User.Type requiredType) {
        if (user == null) {
            return false;
        }
        User.Type userType = user.getType();
        if (userType == User.Type.OWNER) {
            return true;
        }
        if (userType == User.Type.ADMIN) {
            return requiredType != User.Type.OWNER;
        }
        if (userType == User.Type.USER) {
            return requiredType != User.Type.OWNER && requiredType != User.Type.ADMIN;
        }
        if (userType == User.Type.GUEST) {
            return requiredType == User.Type.GUEST;
        }
        return false;
    }

    /**
     * Extract auth token from request header.
     * @param request Request
     * @return Optional of token
     */
    Optional<String> getToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(tokenHeader));
    }

    /**
     * Get the User.Type value of a @Secured annotated element.
     * @param e Annotated element (such as a Method)
     * @return User Type of associated @Secured annotation,
     *         or GUEST if no @Secured is present or if no value is set.
     */
    User.Type getRequiredUserRole(AnnotatedElement e) {
        if (e == null) {
            return User.Type.GUEST;
        }
        Secured secured = e.getAnnotation(Secured.class);
        if (secured == null) {
            return User.Type.GUEST;
        }
        return secured.value();
    }
}
