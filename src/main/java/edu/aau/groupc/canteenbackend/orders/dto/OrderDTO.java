package edu.aau.groupc.canteenbackend.orders.dto;

import edu.aau.groupc.canteenbackend.orders.Order;
import edu.aau.groupc.canteenbackend.orders.OrderHasDish;
import edu.aau.groupc.canteenbackend.orders.OrderHasMenu;
import edu.aau.groupc.canteenbackend.validation.PickupdateConstraint;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// DTO to return a full overview of an order (userId is not returned)
@Data
public class OrderDTO implements Serializable {
    @NotNull(message = "OrderId is required")
    private Integer id;
    // replace with canteen
    @NotNull(message = "Canteen is required")
    private Integer canteenId;
    @NotNull(message = "Dish-List is required")
    private List<@Valid OrderDishDTO> dishes = new LinkedList<>();
    @NotNull(message = "Menu-List is required")
    private List<@Valid OrderMenuDTO> menus = new LinkedList<>();
    private boolean reserveTable = false;
    @NotNull
    @PickupdateConstraint
    private Date pickupDate;

    public static OrderDTO from(Order order) {
        OrderDTO orderDto = new OrderDTO();
        orderDto.setId(order.getId());
        orderDto.setCanteenId(order.getCanteen().getId());
        orderDto.setReserveTable(order.isReserveTable());
        orderDto.setPickupDate(order.getPickUpDate());
        for (OrderHasDish orderHasDish : order.getOrderHasDishes()) {
            OrderDishDTO dishDto = OrderDishDTO.from(orderHasDish);
            orderDto.addDish(dishDto);
        }

        for (OrderHasMenu orderHasMenu : order.getOrderHasMenus()) {
            OrderMenuDTO menuDto = OrderMenuDTO.from(orderHasMenu);
            orderDto.addMenu(menuDto);
        }
        return orderDto;
    }

    private void addDish(OrderDishDTO dishDto) {
        dishes.add(dishDto);
    }

    private void addMenu(OrderMenuDTO menuDto) {
        menus.add(menuDto);
    }


}
