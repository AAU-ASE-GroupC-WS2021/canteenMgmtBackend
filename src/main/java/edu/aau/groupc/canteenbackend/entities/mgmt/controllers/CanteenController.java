package edu.aau.groupc.canteenbackend.entities.mgmt.controllers;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.endpoints.AbstractController;
import edu.aau.groupc.canteenbackend.entities.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.entities.mgmt.dto.CanteenDTO;
import edu.aau.groupc.canteenbackend.entities.mgmt.services.ICanteenService;
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
@RequestMapping("/canteen")
public class CanteenController extends AbstractController {
    private final ICanteenService canteenService;

    @Autowired
    public CanteenController(ICanteenService canteenService) {
        this.canteenService = canteenService;
    }

    @GetMapping
    public ResponseEntity<List<Canteen>> getCanteens()
    {
        return new ResponseEntity<>(canteenService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Canteen> getCanteen(@PathVariable("id") String idString)
    {
        Optional<Canteen> result = canteenService.findById(parseOrThrowHttpException(idString));
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "canteen not found");
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);
    }

    @Secured(User.Type.OWNER)
    @PostMapping
    public ResponseEntity<Canteen> createCanteen(@Valid @RequestBody CanteenDTO newCanteen)
    {
        return new ResponseEntity<>(canteenService.create(newCanteen.toEntity()), HttpStatus.OK);
    }

    @Secured(User.Type.OWNER)
    @PutMapping("/{id}")
    public ResponseEntity<Canteen> updateCanteen(@PathVariable("id") String idString,
                                                 @Valid @RequestBody CanteenDTO updatedCanteen)
    {
        int id = parseOrThrowHttpException(idString);
        Canteen canteenEntity = updatedCanteen.toEntity();
        canteenEntity.setId(id);
        try {
            return new ResponseEntity<>(canteenService.update(canteenEntity), HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "canteen not with given id not found");
        }
    }
}
