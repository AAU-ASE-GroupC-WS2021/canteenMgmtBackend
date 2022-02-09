package edu.aau.groupc.canteenbackend.orders.dto;

import edu.aau.groupc.canteenbackend.menu.Menu;
import edu.aau.groupc.canteenbackend.menu.dto.MenuDTO;
import edu.aau.groupc.canteenbackend.orders.OrderHasMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

// this dto is seperated from the Entity, since the data does not overlap completly
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderMenuDTO extends MenuDTO implements Serializable {
    @NotNull(message = "menuId is required")
    private Integer id;
    @NotNull(message = "count is required")
    @Min(value = 1, message = "count must be > 0")
    private Integer count;

    public static OrderMenuDTO from(OrderHasMenu orderHasMenu) {
        Menu menu = orderHasMenu.getMenu();
        OrderMenuDTO dto = new OrderMenuDTO();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setPrice(menu.getPrice());
        dto.setMenuDay(menu.getName());
        dto.setMenuDishNames(menu.getMenuDishNames());
        dto.setCount(orderHasMenu.getCount());
        return dto;
    }
}
