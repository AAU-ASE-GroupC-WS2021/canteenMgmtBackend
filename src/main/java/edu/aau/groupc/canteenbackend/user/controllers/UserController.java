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

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private IUserService userService;

    @Autowired
    UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public List<User> getUsers() {
        // TODO: Disable later (for security reasons)!
        return this.userService.findAll();
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@RequestBody UserDto newUser) {
        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            return new ResponseEntity<>("Invalid username or password!", HttpStatus.BAD_REQUEST);
        }

        if (newUser.getUsername().length() < 4 || newUser.getPassword().length() < 8) {
            return new ResponseEntity<>("Too short username (min 4 characters) or password (min 8 characters)!", HttpStatus.BAD_REQUEST);
        }

        if (newUser.getUsername().length() > 24 || newUser.getPassword().length() > 120) {
            return new ResponseEntity<>("Too long username (max 24 characters) or password (max 120 characters)!", HttpStatus.BAD_REQUEST);
        }

        if (this.userService.create(newUser.toEntity()) == null) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User registered successfully.", HttpStatus.OK);
    }

}
