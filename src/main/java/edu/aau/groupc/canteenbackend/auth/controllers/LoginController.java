package edu.aau.groupc.canteenbackend.auth.controllers;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.auth.dto.LoginDto;
import edu.aau.groupc.canteenbackend.auth.dto.LogoutDto;
import edu.aau.groupc.canteenbackend.auth.services.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class LoginController {

    private IAuthService authService;

    @Autowired
    LoginController(IAuthService authService) {
        this.authService = authService;
    }

    // If successfully logged in, an "Auth-Token" header will be sent back.
    @PostMapping(value = "/api/auth")
    public ResponseEntity<?> login(@RequestBody LoginDto newAuth) {
        Auth auth;

        if ((auth = this.authService.login(newAuth.getUsername(), newAuth.getPassword())) == null) {
            return new ResponseEntity<>("Incorrect or non-existing login credentials!", HttpStatus.BAD_REQUEST);
        }

        HttpHeaders headers = new HttpHeaders();
        List<String> headersList = new ArrayList<>();
        headersList.add(auth.getToken());
        headers.put("Auth-Token", headersList);

        return new ResponseEntity<>("Logged in successfully.", headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/auth")
    public ResponseEntity<?> logout(@RequestBody LogoutDto existingAuth) {
        if (!this.authService.logout(existingAuth.getUsername(), existingAuth.getToken())) {
            return new ResponseEntity<>("No login found or already logged out!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Logged out successfully.", HttpStatus.OK);
    }

    @GetMapping(value = "/api/auth")
    // TODO: Disable later (for security reasons)!
    public List<Auth> getLogins() {
        return this.authService.findAll();
    }

}
