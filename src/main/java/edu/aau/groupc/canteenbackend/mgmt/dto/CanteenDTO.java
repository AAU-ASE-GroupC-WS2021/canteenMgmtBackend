package edu.aau.groupc.canteenbackend.mgmt.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CanteenDTO implements DTO {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Address is required")
    private String address;
    @Min(value = 0, message = "NumTables must be > 0")
    @NotNull(message = "NumTables is required")
    private Integer numTables;

    @Override
    public Canteen toEntity() {
        return new Canteen()
                .setName(name)
                .setAddress(address)
                .setNumTables(numTables);
    }
}
