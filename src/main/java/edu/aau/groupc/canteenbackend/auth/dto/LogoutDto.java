package edu.aau.groupc.canteenbackend.auth.dto;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.dto.DTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogoutDto logoutDto = (LogoutDto) o;
        return username.equals(logoutDto.username) && token.equals(logoutDto.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token);
    }

    @Override
    public String toString() {
        return "LogoutDto {" +
                "username='" + username + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
