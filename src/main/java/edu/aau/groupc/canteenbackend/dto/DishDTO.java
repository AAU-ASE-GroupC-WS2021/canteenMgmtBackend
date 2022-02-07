package edu.aau.groupc.canteenbackend.dto;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.validation.EnumPattern;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

@SuppressWarnings("ALL")
@Data
public class DishDTO implements DTO {
    @NotBlank(message = "Name is required")
    private String name;
    @Min(value = 0, message = "Price must be > 0")
    @NotNull(message = "Price is required")
    private Float price;
    @NotNull(message = "Type is required")
    @EnumPattern(regexp = "STARTER|MAIN|DESSERT", name = "Type")
    private String type;
    @EnumPattern(regexp = "NOMENUDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY", name="DishDay")
    private String dishDay = "NOMENUDAY";

    public Dish toEntity() {
        return new Dish()
                .setName(getName())
                .setPrice(getPrice())
                .setType(Dish.Type.valueOf(getType()))
                .setDishDay(Dish.DishDay.valueOf(getDishDay()));
    }
}
