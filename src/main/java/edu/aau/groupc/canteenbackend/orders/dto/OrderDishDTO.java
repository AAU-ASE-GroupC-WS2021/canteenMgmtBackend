package edu.aau.groupc.canteenbackend.orders.dto;

import edu.aau.groupc.canteenbackend.dto.DishDTO;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// this dto is seperated from the Entity, since the data does not overlap completly
@Data
public class OrderDishDTO extends DishDTO {
    @NotNull(message = "dishId is required")
    private Integer id;
    @NotNull(message = "count is required")
    @Min(value = 0, message = "count must be > 0")
    private Integer count;

    public OrderDishDTO() {
        // Default
    }

    public OrderDishDTO(String name, Float price, String type, Integer id, Integer count) {
        super(name, price, type);
        this.id = id;
        this.count = count;
    }

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
