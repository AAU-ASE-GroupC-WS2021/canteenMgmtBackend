package edu.aau.groupc.canteenbackend.auth.services;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.user.User;

import java.util.List;

public interface IAuthService {

    Auth login(String username, String password);

    Boolean logout(String token);

    List<Auth> findAll();

    boolean isValidLogin(String token);

    User getUserByToken(String token);
}
