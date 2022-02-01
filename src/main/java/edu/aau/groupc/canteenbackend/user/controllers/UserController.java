package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.endpoints.AbstractController;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.exceptions.CanteenNotFoundException;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserDto;
import edu.aau.groupc.canteenbackend.user.dto.UserReturnDTO;
import edu.aau.groupc.canteenbackend.user.dto.UserUpdateDTO;
import edu.aau.groupc.canteenbackend.user.exceptions.UserNotFoundException;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController extends AbstractController {

    private final IUserService userService;
    private ICanteenService canteenService;

    @Autowired
    UserController(IUserService userService, ICanteenService canteenService) {
        this.userService = userService;
        this.canteenService = canteenService;
    }

    @PostMapping(value = "/api/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto newUser) {
        if (this.userService.create(newUser.toEntity()) == null) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }


    // TODO: Add tests
    @Secured(User.Type.OWNER)
    @PostMapping(value = "/api/owner/user")
    public ResponseEntity<UserReturnDTO> createAdmin(@Valid @RequestBody UserDto newUser) {
        User newAdmin = newUser.toEntity();
        newAdmin.setType(User.Type.ADMIN);

        Optional<Canteen> homeCanteen = canteenService.findById(newUser.getCanteenID());
        if (homeCanteen.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newAdmin.setHomeCanteen(homeCanteen.get());

        if (this.userService.create(newAdmin) == null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(UserReturnDTO.from(newAdmin), HttpStatus.OK);
    }

    @Secured(User.Type.OWNER)
    @PutMapping(value = "/api/owner/user/{id}")
    public ResponseEntity<UserReturnDTO> updateAccount(@PathVariable("id") String idString,
                                                       @Valid @RequestBody UserUpdateDTO updateInfo) {

        int id = parseOrThrowHttpException(idString);
        try {
            return new ResponseEntity<>(UserReturnDTO.from(userService.updateUser(id, updateInfo)), HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with given id not found");
        } catch (CanteenNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "canteen with given id not found");
        }
    }

}
