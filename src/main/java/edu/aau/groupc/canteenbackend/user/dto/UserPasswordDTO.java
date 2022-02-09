package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.user.User;
import lombok.Data;
import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class UserPasswordDTO implements DTO, Serializable {

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

    @Override
    public User toEntity() {
        throw new NotImplementedException();
    }

    public String toJSONString() {
        return "{ \"username\": \"" + username + "\", \"passwordOld\": \"" + passwordOld + "\", \"passwordNew\": \"" + passwordNew + "\" }";
    }

}
