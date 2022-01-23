package edu.aau.groupc.canteenbackend.user.services;

import edu.aau.groupc.canteenbackend.user.User;

import java.util.List;

public interface IUserService {

    List<User> findAll();
    boolean ownerExists();
    User create(User user);

}
