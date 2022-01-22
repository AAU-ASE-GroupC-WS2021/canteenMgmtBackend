package edu.aau.groupc.canteenbackend.auth.dto;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.dto.DTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements DTO, Serializable {

    private String username;
    private String password;

    private LoginDto() {}

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Auth toEntity() {
        return new Auth(getUsername());
    }

}
