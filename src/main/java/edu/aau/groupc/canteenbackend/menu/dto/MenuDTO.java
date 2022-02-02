package edu.aau.groupc.canteenbackend.menu.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.validation.EnumPattern;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@SuppressWarnings("ALL")
@Data
public class MenuDTO implements DTO {
    @NotBlank(message = "Name is required")
    private List<Dish> menuDishes;
    @EnumPattern(regexp = "NOMENUDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY", name="DishDay")
    private Dish.DishDay dishDay;


    public Menu toEntity() {
        return new Menu()
                .setMenuDishes(getMenuDishes())
                .setMenuDay(getDishDay());
    }
}
