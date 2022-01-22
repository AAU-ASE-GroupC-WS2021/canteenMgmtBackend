package edu.aau.groupc.canteenbackend.auth.controllers;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.dto.LoginDto;
import edu.aau.groupc.canteenbackend.auth.dto.LogoutDto;
import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {

    private IAuthService authService;

    @Autowired
    LoginController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/api/auth")
    public ResponseEntity<?> login(@RequestBody LoginDto newAuth) {
        if (this.authService.login(newAuth.getUsername(), newAuth.getPassword()) == null) {
            return new ResponseEntity<>("Incorrect login credentials!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Logged in successfully.", HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/auth")
    public ResponseEntity<?> logout(@RequestBody LogoutDto existingAuth) {
        if (!this.authService.logout(existingAuth.getUsername(), existingAuth.getToken())) {
            return new ResponseEntity<>("No login found or already logged out!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Logged out successfully.", HttpStatus.OK);
    }

    @GetMapping(value = "/api/auth")
    public List<Auth> getLogins() {
        return this.authService.findAll();
    }

}
