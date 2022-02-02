package edu.aau.groupc.canteenbackend.orders.dto;

import edu.aau.groupc.canteenbackend.validation.PickupdateConstraint;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// DTO to return a full overview of an order (userId is not returned)
public class OrderDTO {
    @NotNull(message = "OrderId is required")
    Integer id;
    // replace with canteen
    @NotNull(message = "Canteen is required")
    Integer canteenId;
    @NotNull(message = "Dish-List is required")
    @Size(min = 1, message = "Dish-List must not be empty")
    List<@Valid OrderDishDTO> dishes = new LinkedList<>();
    boolean reserveTable = false;
    @NotNull
    @PickupdateConstraint
    Date pickupDate;

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

    public boolean isReserveTable() {
        return reserveTable;
    }

    public void setReserveTable(boolean reserveTable) {
        this.reserveTable = reserveTable;
    }

    public Date getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Date pickupDate) {
        this.pickupDate = pickupDate;
    }
}
