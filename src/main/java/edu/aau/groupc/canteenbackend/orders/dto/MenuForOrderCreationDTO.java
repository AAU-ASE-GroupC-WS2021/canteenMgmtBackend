package edu.aau.groupc.canteenbackend.orders.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

// this dto is seperated from the Entity, since the data does not overlap completly
@Data
public class MenuForOrderCreationDTO implements Serializable {

    @NotNull(message = "MenuId is required")
    private Integer id;

    @Min(value = 1, message = "Count must be > 0")
    @NotNull(message = "Count is required")
    private Integer count;
}
