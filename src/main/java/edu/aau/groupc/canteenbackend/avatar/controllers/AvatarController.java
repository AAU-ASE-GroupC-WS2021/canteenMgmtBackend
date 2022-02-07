package edu.aau.groupc.canteenbackend.avatar.controllers;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.avatar.Avatar;
import edu.aau.groupc.canteenbackend.avatar.dto.AvatarDto;
import edu.aau.groupc.canteenbackend.avatar.services.IAvatarService;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AvatarController {

    private IAvatarService avatarService;

    @Autowired
    AvatarController(IAvatarService authService) {
        this.avatarService = authService;
    }

    @Secured
    @PostMapping(value = "/api/avatar")
    public ResponseEntity<String> updateOrAddAvatar(@RequestBody AvatarDto avatarDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        // If a user wants to change their own avatar
        if (user.getUsername().equals(avatarDto.getUsername())) {
            avatarService.updateOrAddAvatar(avatarDto.getUsername(), avatarDto.getAvatar());
            return new ResponseEntity<>("Successfully updated avatar.", HttpStatus.OK);
        }

        // If an admin or an owner wants to change someone's avatar
        else if (user.getType().equals(User.Type.ADMIN) || user.getType().equals(User.Type.OWNER)) {
            avatarService.updateOrAddAvatar(avatarDto.getUsername(), avatarDto.getAvatar());
            return new ResponseEntity<>("You have successfully updated " + avatarDto.getUsername() + "'s avatar.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Could not update avatar!", HttpStatus.BAD_REQUEST);
    }

    @Secured
    @DeleteMapping(value = "/api/avatar")
    public ResponseEntity<String> removeAvatar(@RequestBody String username, HttpServletRequest req) {
        if (avatarService.getAvatar(username) == null) {
            return new ResponseEntity<>("Avatar does not exist for that username!", HttpStatus.BAD_REQUEST);
        }

        User user = (User) req.getAttribute("user");

        // If a user wants to delete their own avatar
        if (user.getUsername().equals(username)) {
            avatarService.deleteAvatar(username);
            return new ResponseEntity<>("Successfully deleted avatar.", HttpStatus.OK);
        }

        // If an admin or an owner wants to delete someone's avatar
        else if (user.getType().equals(User.Type.ADMIN) || user.getType().equals(User.Type.OWNER)) {
            avatarService.deleteAvatar(username);
            return new ResponseEntity<>("You have successfully deleted " + username + "'s avatar.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Could not delete avatar!", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/api/avatar")
    public ResponseEntity<AvatarDto> getAvatar(@RequestBody String username) {
        Avatar avatar;

        if ((avatar = avatarService.getAvatar(username)) == null) {
            return new ResponseEntity<>(new AvatarDto(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new AvatarDto(avatar.getUsername(), avatar.getAvatar()), HttpStatus.OK);
    }

}
