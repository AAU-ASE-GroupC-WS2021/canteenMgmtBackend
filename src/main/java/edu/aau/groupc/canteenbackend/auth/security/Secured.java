package edu.aau.groupc.canteenbackend.auth.security;

import edu.aau.groupc.canteenbackend.user.User;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Retention(RetentionPolicy.RUNTIME)
@Target({METHOD})
public @interface Secured {
    User.Type value() default User.Type.GUEST;
}