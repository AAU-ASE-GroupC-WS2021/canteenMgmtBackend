package edu.aau.groupc.canteenbackend.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = NoEmptyOrderValidator.class)
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface NoEmptyOrderConstraint {
    String message() default "At least 1 Dish or order required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
