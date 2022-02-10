package edu.aau.groupc.canteenbackend.auth.dto;

import edu.aau.groupc.canteenbackend.auth.Auth;
import edu.aau.groupc.canteenbackend.dish.dto.DTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginDto loginDto = (LoginDto) o;
        return username.equals(loginDto.username) && password.equals(loginDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "LoginDto {" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String toJSONString() {
        return "{ \"username\": \"" + username + "\", \"password\":\"" + password + "\" }";
    }
}
