package edu.aau.groupc.canteenbackend.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

public class PickupdateValidator implements ConstraintValidator<PickupdateConstraint, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        return value.after(new Date(new Date().getTime() + 3600 * 1000));
    }
}
