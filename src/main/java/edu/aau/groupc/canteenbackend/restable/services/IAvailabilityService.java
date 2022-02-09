package edu.aau.groupc.canteenbackend.restable.services;

import edu.aau.groupc.canteenbackend.restable.Availability;
import edu.aau.groupc.canteenbackend.restable.dto.AvailabilityDTO;
import edu.aau.groupc.canteenbackend.restable.exceptions.SlotNotFoundException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public interface IAvailabilityService {
    List<Availability> findAll();

    Optional<Availability> findById(int slot);
    Availability findEntityById(int id) throws ResponseStatusException;

    Availability create(Availability availability);

    Availability update(Integer slot, AvailabilityDTO availabilityDTO) throws SlotNotFoundException;
}
