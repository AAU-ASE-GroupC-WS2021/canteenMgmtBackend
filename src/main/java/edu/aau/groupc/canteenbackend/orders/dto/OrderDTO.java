package edu.aau.groupc.canteenbackend.orders.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;

// DTO to return a full overview of an order (userId is not returned)
public class OrderDTO {
    @NotNull(message = "OrderId is required")
    Integer id;
    // replace with canteen
    @NotNull(message = "Canteen is required")
    Integer canteenId;
    @NotNull(message = "DishList is required")
    @Min(1)
    List<OrderDishDTO> dishes = new LinkedList<>();

    public OrderDTO() {
        // Default
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(Integer canteenId) {
        this.canteenId = canteenId;
    }

    public List<OrderDishDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDishDTO> dishes) {
        this.dishes = dishes;
    }

    public void addDish(OrderDishDTO dish) {
        this.dishes.add(dish);
    }
}
