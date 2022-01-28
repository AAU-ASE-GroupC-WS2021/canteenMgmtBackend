package edu.aau.groupc.canteenbackend.util;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Let test classes implement this interface to gain access to "javax.validation" assertion methods.
 */
public interface ValidationTest {
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    default <T> void assertValid(T o) {
        Set<ConstraintViolation<T>> violations = validator.validate(o);
        assertTrue(violations.isEmpty());
    }

    default <T> void assertInvalid(T o) {
        Set<ConstraintViolation<T>> violations = validator.validate(o);
        assertFalse(violations.isEmpty());
    }
}
