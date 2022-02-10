package edu.aau.groupc.canteenbackend.user.controllers;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.AbstractController;
import edu.aau.groupc.canteenbackend.user.User;
import edu.aau.groupc.canteenbackend.user.dto.UserPasswordDTO;
import edu.aau.groupc.canteenbackend.user.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class UserPasswordController extends AbstractController {

    private final IUserService userService;

    @Autowired
    public UserPasswordController(IUserService userService) {
        this.userService = userService;
    }

    @Secured
    @PostMapping(value = "/api/password")
    @Transactional
    public ResponseEntity<String> changePassword(@Valid @RequestBody UserPasswordDTO newPassword, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        if (newPassword == null) {
            return new ResponseEntity<>("No data received!", HttpStatus.BAD_REQUEST);
        }
        else if (user == null) {
            return new ResponseEntity<>("No user associated with the request!", HttpStatus.BAD_REQUEST);
        }
        else if (!newPassword.getUsername().equals(user.getUsername())) {
            return new ResponseEntity<>("You can change password only for your account!", HttpStatus.BAD_REQUEST);
        }
        else if (!newPassword.getPasswordOld().equals(user.getPassword())) {
            return new ResponseEntity<>("Old password is not correct!", HttpStatus.BAD_REQUEST);
        }
        else if (!userService.updatePassword(newPassword.getUsername(), newPassword.getPasswordNew())) {
            return new ResponseEntity<>("Could not update password!", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Password changed successfully.", HttpStatus.OK);
    }

}
