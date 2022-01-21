package edu.aau.groupc.canteenbackend.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {

    private final String username;
    private final String password;

}
