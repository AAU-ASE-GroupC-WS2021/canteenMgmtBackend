package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.user.User;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

@Data
public class UserDto implements DTO, Serializable {

    @NotBlank(message = "Username is required")
    @Length(min = 3, message = "Username must be at least 3 characters long!")
    @Length(max = 24, message = "Username cannot be longer than 24 characters!")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username can contain only alphanumeric characters!")
    private String username;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^[a-fA-F0-9]+$", message = "Password can contain only hexadecimal digits!")
    @Length(min = 64, max = 64, message = "Password must be exactly 64 characters long!")
    private String password;

    private Integer canteenID;

    private UserDto() {}

    public UserDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public User toEntity() {
        return new User(getUsername(), getPassword(), User.Type.USER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return username.equals(userDto.username) && password.equals(userDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }

    @Override
    public String toString() {
        return "UserDto {" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
