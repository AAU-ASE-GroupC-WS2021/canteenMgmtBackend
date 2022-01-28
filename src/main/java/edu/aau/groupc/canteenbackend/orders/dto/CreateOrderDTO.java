package edu.aau.groupc.canteenbackend.orders.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

// this dto is seperated from the Entity, since the data does not overlap completly
@Data
public class CreateOrderDTO {

    // TODO: check if the user may be fetched by the authetication
    @NotNull(message = "UserId is required")
    private Integer userId;

    @NotNull(message = "CanteenId is required")
    private Integer canteenId;

    @NotNull
    @Size(min = 1)
    private List<@Valid DishForOrderCreationDTO> dishes;

    // TODO ADD MENU LIST


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(Integer canteenId) {
        this.canteenId = canteenId;
    }

    public List<DishForOrderCreationDTO> getDishes() {
        return dishes;
    }

    public void setDishes(List<DishForOrderCreationDTO> dishes) {
        this.dishes = dishes;
    }
}
