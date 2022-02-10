package edu.aau.groupc.canteenbackend.avatar.dto;

import edu.aau.groupc.canteenbackend.avatar.Avatar;
import edu.aau.groupc.canteenbackend.dish.dto.DTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class AvatarDto implements DTO, Serializable {

    private String username;
    private String avatar;

    public AvatarDto() {}

    public AvatarDto(String username, String avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    @Override
    public Avatar toEntity() {
        return new Avatar(username, avatar);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarDto avatarDto = (AvatarDto) o;
        return username.equals(avatarDto.username) && avatar.equals(avatarDto.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, avatar);
    }

    @Override
    public String toString() {
        return toJSONString();
    }

    public String toJSONString() {
        return "{ \"username\": \"" + username + "\", \"avatar\": \"" + avatar + "\" }";
    }
}
