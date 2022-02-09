package edu.aau.groupc.canteenbackend.validation;

import edu.aau.groupc.canteenbackend.orders.dto.CreateOrderDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NoEmptyOrderValidator implements ConstraintValidator<NoEmptyOrderConstraint, CreateOrderDTO> {

    @Override
    public boolean isValid(CreateOrderDTO value, ConstraintValidatorContext context) {
        return (value.getDishes() != null && !value.getDishes().isEmpty()) ||
                (value.getMenus() != null && !value.getMenus().isEmpty());
    }
}
