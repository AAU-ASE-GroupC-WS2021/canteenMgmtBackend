package edu.aau.groupc.canteenbackend.orders.dto;

import edu.aau.groupc.canteenbackend.validation.NoEmptyOrderConstraint;
import edu.aau.groupc.canteenbackend.validation.PickupdateConstraint;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// this dto is seperated from the Entity, since the data does not overlap completly
@Data
@NoEmptyOrderConstraint
public class CreateOrderDTO implements Serializable {

    boolean reserveTable = false;

    @NotNull(message = "CanteenId is required")
    private Integer canteenId;

    @NotNull(message = "Dish-List is required")
    private List<@Valid DishForOrderCreationDTO> dishes = new LinkedList<>();
    @NotNull(message = "Menu-List is required")
    private List<@Valid MenuForOrderCreationDTO> menus = new LinkedList<>();
    @NotNull
    @PickupdateConstraint
    private Date pickupDate;
}
