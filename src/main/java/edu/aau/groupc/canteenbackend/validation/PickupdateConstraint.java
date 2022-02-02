package edu.aau.groupc.canteenbackend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = PickupdateValidator.class)
@Retention(RUNTIME)
@Target(ElementType.FIELD)
public @interface PickupdateConstraint {
    String message() default "Pickup date must be at least 1 hour from now";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
