package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.mgmt.dto.CanteenDTO;
import edu.aau.groupc.canteenbackend.user.User;
import lombok.Data;
import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.validator.constraints.Length;

import javax.naming.OperationNotSupportedException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

@Data
public class UserUpdateDTO implements DTO, Serializable {

    @Length(min = 3, message = "Username must be at least 3 characters long!")
    @Length(max = 24, message = "Username cannot be longer than 24 characters!")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username can contain only alphanumeric characters!")
    private String username;

    @Pattern(regexp = "^[a-fA-F0-9]+$", message = "Password can contain only hexadecimal digits!")
    @Length(min = 64, max = 64, message = "Password must be exactly 64 characters long!")
    private String password;

    private User.Type type;
    private Integer canteenID;

    @Override
    public User toEntity() {
        throw new NotImplementedException();
    }

    public static UserUpdateDTO create(String username, String password, User.Type type, Integer canteenID) {
        UserUpdateDTO u = new UserUpdateDTO();
        u.setUsername(username);
        u.setPassword(password);
        u.setType(type);
        u.setCanteenID(canteenID);
        return u;
    }
}
