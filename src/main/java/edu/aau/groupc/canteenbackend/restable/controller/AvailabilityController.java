package edu.aau.groupc.canteenbackend.restable.controller;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.endpoints.AbstractController;
import edu.aau.groupc.canteenbackend.restable.dto.AvailabilityDTO;
import edu.aau.groupc.canteenbackend.restable.Availability;
import edu.aau.groupc.canteenbackend.restable.services.AvailabilityService;
import edu.aau.groupc.canteenbackend.restable.services.IAvailabilityService;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api" )
public class AvailabilityController extends AbstractController {
    private final IAvailabilityService availabilityService;

    @Autowired
    public AvailabilityController(IAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/reserve")
    public ResponseEntity<List<Availability>> getSlots()
    {
        return new ResponseEntity<>(AvailabilityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/reserve/{slot}")
    public ResponseEntity<Availability> getAvailability(@PathVariable("slot") String idString)
    {
        Optional<Availability> result = availabilityService.findById(parseOrThrowHttpException(idString));
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Slot not found");
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @Secured(User.Type.OWNER)
    @PostMapping("/reserve")
    public ResponseEntity<Availability> createAvailability(@Valid @RequestBody AvailabilityDTO newAvailability)
    {
        return new ResponseEntity<>(availabilityService.create(newAvailability.toEntity()), HttpStatus.OK);
    }

}
