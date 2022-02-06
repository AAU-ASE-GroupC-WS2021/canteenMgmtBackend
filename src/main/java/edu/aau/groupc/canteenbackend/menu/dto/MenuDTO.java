package edu.aau.groupc.canteenbackend.menu.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.validation.EnumPattern;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@SuppressWarnings("ALL")
@Data
public class MenuDTO implements DTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 0, message = "Price must be > 0")
    @NotNull(message = "Price is required")
    private Float price;

//    @OneToMany(cascade = {CascadeType.REFRESH})
    private List<String> menuDishNames;

    @EnumPattern(regexp = "NOMENUDAY|MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY", name="MenuDay")
    private String menuDay = "NOMENUDAY";


    public Menu toEntity() {
        return new Menu()
                .setName(getName())
                .setPrice(getPrice())
                .setMenuDishNames(getMenuDishNames())
                .setMenuDay(Menu.MenuDay.valueOf(getMenuDay()));
    }
}
