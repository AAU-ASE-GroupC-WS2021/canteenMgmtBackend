package edu.aau.groupc.canteenbackend.user.dto;

import edu.aau.groupc.canteenbackend.user.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserReturnDTO implements Serializable {

    private long id;
    private String username;
    private User.Type type;

    private Integer canteenID;

    public static UserReturnDTO from(User user) {
        UserReturnDTO dto = new UserReturnDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setType(user.getType());
        if (user.getHomeCanteen() != null) {
            dto.setCanteenID(user.getHomeCanteen().getId());
        }
        return dto;
    }

}
