package edu.aau.groupc.canteenbackend.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
public class ValidationTestController {

    @PostMapping(value = "/test/validation")
    public ResponseEntity<String> createDish(@Valid @RequestBody ValidationTestObject o)
    {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }
}
