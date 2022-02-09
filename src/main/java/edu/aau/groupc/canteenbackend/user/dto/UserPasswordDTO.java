package edu.aau.groupc.canteenbackend.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

@Data
public class UserPasswordDTO implements Serializable {

    @NotBlank(message = "Username is required")
    @Length(min = 3, message = "Username must be at least 3 characters long!")
    @Length(max = 24, message = "Username cannot be longer than 24 characters!")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username can contain only alphanumeric characters!")
    private String username;

    @NotBlank(message = "Old password is required")
    @Pattern(regexp = "^[a-fA-F0-9]+$", message = "Password can contain only hexadecimal digits!")
    @Length(min = 64, max = 64, message = "Password must be exactly 64 characters long!")
    private String passwordOld;

    @NotBlank(message = "New password is required")
    @Pattern(regexp = "^[a-fA-F0-9]+$", message = "Password can contain only hexadecimal digits!")
    @Length(min = 64, max = 64, message = "Password must be exactly 64 characters long!")
    private String passwordNew;

    private UserPasswordDTO() {}

    public UserPasswordDTO(String username, String passwordOld, String passwordNew) {
        this.username = username;
        this.passwordOld = passwordOld;
        this.passwordNew = passwordNew;
    }

    public String toJSONString() {
        return "{ \"username\": \"" + username + "\", \"passwordOld\": \"" + passwordOld + "\", \"passwordNew\": \"" + passwordNew + "\" }";
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPasswordDTO that = (UserPasswordDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(passwordOld, that.passwordOld) && Objects.equals(passwordNew, that.passwordNew);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passwordOld, passwordNew);
    }
}
