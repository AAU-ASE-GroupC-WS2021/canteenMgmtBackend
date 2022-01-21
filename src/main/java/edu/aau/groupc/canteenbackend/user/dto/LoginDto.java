package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.user.User;
import lombok.Data;
import lombok.extern.java.Log;

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
    public DBEntity toEntity() {
        return new User(username, password, User.Type.USER);
    }

}
