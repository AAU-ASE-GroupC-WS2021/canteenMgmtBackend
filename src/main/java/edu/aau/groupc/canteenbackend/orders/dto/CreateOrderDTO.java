package edu.aau.groupc.canteenbackend.orders.dto;

import edu.aau.groupc.canteenbackend.validation.PickupdateConstraint;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

// this dto is seperated from the Entity, since the data does not overlap completly
@Data
public class CreateOrderDTO implements Serializable {

    boolean reserveTable = false;

    @NotNull(message = "CanteenId is required")
    private Integer canteenId;

    @NotNull(message = "Dish-List is required")
    @Size(min = 1, message = "Dish-List must not be empty")
    private List<@Valid DishForOrderCreationDTO> dishes;
    @NotNull
    @PickupdateConstraint
    Date pickupDate;
    // TODO: check if the user may be fetched by the authetication
    @NotNull(message = "UserId is required")
    private Long userId;

    // TODO ADD MENU LIST
}
