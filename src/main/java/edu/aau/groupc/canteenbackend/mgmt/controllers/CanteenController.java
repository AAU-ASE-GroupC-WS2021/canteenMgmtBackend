package edu.aau.groupc.canteenbackend.mgmt.controllers;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.AbstractController;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.dto.CanteenDTO;
import edu.aau.groupc.canteenbackend.mgmt.exceptions.CanteenNotFoundException;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
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
public class CanteenController extends AbstractController {
    private final ICanteenService canteenService;

    @Autowired
    public CanteenController(ICanteenService canteenService) {
        this.canteenService = canteenService;
    }

    @GetMapping("/canteen")
    public ResponseEntity<List<Canteen>> getCanteens()
    {
        return new ResponseEntity<>(canteenService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/canteen/{id}")
    public ResponseEntity<Canteen> getCanteen(@PathVariable("id") String idString)
    {
        Optional<Canteen> result = canteenService.findById(parseOrThrowHttpException(idString));
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "canteen not found");
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @Secured(User.Type.OWNER)
    @PostMapping("/canteen")
    public ResponseEntity<Canteen> createCanteen(@Valid @RequestBody CanteenDTO newCanteen)
    {
        return new ResponseEntity<>(canteenService.create(newCanteen.toEntity()), HttpStatus.OK);
    }

    @Secured(User.Type.OWNER)
    @PutMapping("/canteen/{id}")
    public ResponseEntity<Canteen> updateCanteen(@PathVariable("id") String idString,
                                                 @Valid @RequestBody CanteenDTO updatedCanteen)
    {
        int id = parseOrThrowHttpException(idString);
        try {
            return new ResponseEntity<>(canteenService.update(id, updatedCanteen), HttpStatus.OK);
        } catch (CanteenNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "canteen with given id not found");
        }
    }
}
