package edu.aau.groupc.canteenbackend.auth.services;

import edu.aau.groupc.canteenbackend.auth.Auth;

import java.util.List;

public interface IAuthService {

    Auth login(String username, String password);

    Boolean logout(String username, String token);

    List<Auth> findAll();

}
