package edu.aau.groupc.canteenbackend.validation;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ValidationTestObject {
    @NotNull(message = "Type is required")
    @EnumPattern(regexp = "STARTER|MAIN|DESSERT", name="someEnum")
    private String someEnum;
}
