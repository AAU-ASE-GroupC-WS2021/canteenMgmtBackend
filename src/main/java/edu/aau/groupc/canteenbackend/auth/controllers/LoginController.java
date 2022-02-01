package edu.aau.groupc.canteenbackend.auth.controllers;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.dto.LoginDto;
import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private IAuthService authService;

    @Autowired
    LoginController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/api/auth")
    public ResponseEntity<String> login(@RequestBody LoginDto newAuth) {
        Auth auth;

        if ((auth = this.authService.login(newAuth.getUsername(), newAuth.getPassword())) == null) {
            return new ResponseEntity<>("Incorrect or non-existing login credentials!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(auth.getToken(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/auth")
    public ResponseEntity<String> logout(@RequestBody String token) {
        if (!this.authService.logout(token)) {
            return new ResponseEntity<>("No login found or already logged out!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Logged out successfully.", HttpStatus.OK);
    }

}
