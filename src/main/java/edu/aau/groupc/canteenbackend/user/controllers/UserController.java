package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserDto;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
        // TODO: Disable this feature (enabled only for testing purposes).
        return userService.findAll();
    }

    @PostMapping(value = "/user")
    public User createUser(@RequestBody UserDto newUser) {
        return this.userService.create(newUser.toEntity());
    }

    // TODO: Enable creating GUEST accounts in case guests can add items to cart. Or a user is a GUEST if they are not logged in?

}
