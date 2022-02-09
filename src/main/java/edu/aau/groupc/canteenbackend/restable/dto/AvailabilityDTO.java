package edu.aau.groupc.canteenbackend.restable.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.restable.Availability;
import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AvailabilityDTO implements DTO{

    @NotBlank(message = "Slot is required")
    @Min(value = 0, message = "TimeSlot must be > 0 ")
    @Max(value = 23, message = "Timeslot must be < 24 ")
    private Integer slot;

    @Min(value = 0, message = "numAvailableTables must be > 0")
    @NotNull(message = "numAvailableTables is required")
    private Integer numAvailableTables;

    @Override
    public Availability toEntity() {
        return new Availability()
                .setSlot(slot)
                .setNumAvailableTables(numAvailableTables);
    }

    public static AvailabilityDTO create(Integer slot, Integer numAvailableTablesTables) {
        AvailabilityDTO c = new AvailabilityDTO();
        c.setSlot(slot);
        c.setNumAvailableTables(numAvailableTablesTables);
        return c;
    }
}
