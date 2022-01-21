package edu.aau.groupc.canteenbackend.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserDto implements Serializable {

    private final String username;
    private final String password;

}
