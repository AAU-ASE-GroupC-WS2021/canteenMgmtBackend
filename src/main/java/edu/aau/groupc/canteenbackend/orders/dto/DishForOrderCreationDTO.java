package edu.aau.groupc.canteenbackend.orders.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// this dto is seperated from the Entity, since the data does not overlap completly
@Data
public class DishForOrderCreationDTO {

    @NotNull(message = "DishId is required")
    private Integer id;

    @Min(value = 0, message = "Count must be > 0")
    @NotNull(message = "Count is required")
    private Integer count;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
