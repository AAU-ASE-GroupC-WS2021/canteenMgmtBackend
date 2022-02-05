package edu.aau.groupc.canteenbackend.orders.dto;

import edu.aau.groupc.canteenbackend.dto.DishDTO;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.orders.OrderHasDish;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// this dto is seperated from the Entity, since the data does not overlap completly
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDishDTO extends DishDTO {
    @NotNull(message = "dishId is required")
    private Integer id;
    @NotNull(message = "count is required")
    @Min(value = 1, message = "count must be > 0")
    private Integer count;

    public static OrderDishDTO from(OrderHasDish orderHasDish) {
        Dish dish = orderHasDish.getDish();
        OrderDishDTO dto = new OrderDishDTO();
        dto.setId(dish.getId());
        dto.setType(dish.getType().name());
        dto.setPrice(dish.getPrice());
        dto.setName(dish.getName());
        dto.setCount(orderHasDish.getCount());
        return dto;
    }
}
