package edu.aau.groupc.canteenbackend.auth.security;

import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("authtest")
public class AuthTestController {

    @GetMapping(value = "/unsecured")
    public ResponseEntity<String> unsecured()
    {
        return new ResponseEntity<>("Data", new HttpHeaders(), HttpStatus.OK);
    }

    @Secured
    @GetMapping(value = "/securedNoType")
    public ResponseEntity<String> securedNoType()
    {
        return new ResponseEntity<>("Data", new HttpHeaders(), HttpStatus.OK);
    }

    @Secured(User.Type.GUEST)
    @GetMapping(value = "/securedGuest")
    public ResponseEntity<String> securedGuest()
    {
        return new ResponseEntity<>("Data", new HttpHeaders(), HttpStatus.OK);
    }

    @Secured(User.Type.USER)
    @GetMapping(value = "/securedUser")
    public ResponseEntity<String> securedUser()
    {
        return new ResponseEntity<>("Data", new HttpHeaders(), HttpStatus.OK);
    }

    @Secured(User.Type.ADMIN)
    @GetMapping(value = "/securedAdmin")
    public ResponseEntity<String> securedAdmin()
    {
        return new ResponseEntity<>("Data", new HttpHeaders(), HttpStatus.OK);
    }

    @Secured(User.Type.OWNER)
    @GetMapping(value = "/securedOwner")
    public ResponseEntity<String> securedOwner()
    {
        return new ResponseEntity<>("Data", new HttpHeaders(), HttpStatus.OK);
    }
}
