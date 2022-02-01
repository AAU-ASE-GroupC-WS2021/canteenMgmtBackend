package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.mgmt.services.ICanteenService;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserDto;
import edu.aau.groupc.canteenbackend.user.dto.UserReturnDTO;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

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
    @PostMapping(value = "/api/create-admin")
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

}
