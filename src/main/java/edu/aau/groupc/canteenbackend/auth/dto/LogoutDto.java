package edu.aau.groupc.canteenbackend.auth.dto;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.dto.DTO;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogoutDto implements DTO, Serializable {

    private String username;
    private String token;

    private LogoutDto() {}

    public LogoutDto(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public Auth toEntity() {
        return new Auth(getUsername(), getToken());
    }

}
