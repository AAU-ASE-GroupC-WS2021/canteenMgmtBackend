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
import edu.aau.groupc.canteenbackend.user.exceptions.UsernameConflictException;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController extends AbstractController {

    private final IUserService userService;

    @Autowired
    UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/api/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto newUser) {
        if (this.userService.create(newUser.toEntity()) == null) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }

    @Secured(User.Type.OWNER)
    @PostMapping(value = "/api/owner/user")
    @Transactional
    public ResponseEntity<UserReturnDTO> createAdmin(@Valid @RequestBody UserDto newUser) {
        try {
            return new ResponseEntity<>(UserReturnDTO.from(userService.create(newUser, User.Type.ADMIN)), HttpStatus.OK);
        } catch (UsernameConflictException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exists");
        } catch (CanteenNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "canteen with given id not found");
        }
    }

    @Secured(User.Type.OWNER)
    @PutMapping(value = "/api/owner/user/{id}")
    @Transactional
    public ResponseEntity<UserReturnDTO> updateUser(@PathVariable("id") String idString,
                                                       @Valid @RequestBody UserUpdateDTO updateInfo) {

        int id = parseOrThrowHttpException(idString);
        try {
            return new ResponseEntity<>(UserReturnDTO.from(userService.updateUser(id, updateInfo)), HttpStatus.OK);
        } catch (UsernameConflictException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exists");
        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user with given id not found");
        } catch (CanteenNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "canteen with given id not found");
        }
    }

}
