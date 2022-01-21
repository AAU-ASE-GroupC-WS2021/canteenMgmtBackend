package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.RegisterUserDto;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class UserController {

    private IUserService userService;

    @Autowired
    UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user")
    public List<User> getUsers() {
        // TODO: disable this feature (enabled only for testing purposes)
        return userService.findAll();
    }

    @PostMapping(value = "/user")
    public User createUser(@RequestBody RegisterUserDto newUser) {
        return this.userService.create(newUser.toEntity());
    }

}
