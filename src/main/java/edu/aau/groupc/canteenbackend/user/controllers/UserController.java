package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserDto;
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

@RestController
public class UserController {

    private IUserService userService;

    @Autowired
    UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/api/register")
    public List<User> getUsers() {
        // TODO: Disable later (for security reasons)!
        return this.userService.findAll();
    }

    @PostMapping(value = "/api/register")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto newUser) {
        if (this.userService.create(newUser.toEntity()) == null) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }

}