package edu.aau.groupc.canteenbackend.restable.services;

import edu.aau.groupc.canteenbackend.restable.Availability;
import edu.aau.groupc.canteenbackend.restable.dto.AvailabilityDTO;
import edu.aau.groupc.canteenbackend.restable.exceptions.SlotNotFoundException;
import edu.aau.groupc.canteenbackend.restable.repos.IAvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class AvailabilityService implements IAvailabilityService {

    private final IAvailabilityRepository availabilityRepository;

    @Autowired
    public AvailabilityService(IAvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<Availability> findAll() {return availabilityRepository.findAllBySlotAsc();
    }

    @Override
    public Optional<Availability> findById(int slot) {
        return availabilityRepository.findById(slot);
    }

    @Override
    public Availability findEntityById(int slot) throws ResponseStatusException {
        Optional<Availability> availabilityOptional = availabilityRepository.findById(slot);
        return availabilityOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Timeslot not found"));
    }

    @Override
    public Availability create(Availability availability) {
        return availabilityRepository.save(availability);
    }

    @Override
    public Availability update(Integer slot, AvailabilityDTO availability) throws SlotNotFoundException {
        if (!availabilityRepository.existsById(slot)) {
            throw new SlotNotFoundException();
        }
        Availability entity = availability.toEntity().setSlot(slot);
        return availabilityRepository.save(entity);
    }

}
