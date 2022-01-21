package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.user.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUserDto implements DTO, Serializable {

    private String username;
    private String password;

    private RegisterUserDto() {}

    public RegisterUserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public User toEntity() {
        return new User(username, password, User.Type.USER);
    }

}
