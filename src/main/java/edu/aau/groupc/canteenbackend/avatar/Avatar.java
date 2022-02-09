package edu.aau.groupc.canteenbackend.avatar;

import edu.aau.groupc.canteenbackend.entities.DBEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Data
@Entity
@Table(name = "avatar", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
})
public class Avatar implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private byte[] avatar;

    public Avatar() {}

    public Avatar(String username, String avatar) {
        super();
        this.username = username;
        this.avatar = Base64.getDecoder().decode(avatar);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return Base64.getEncoder().encodeToString(this.avatar);
    }

    public void setAvatar(String avatar) {
        this.avatar = Base64.getDecoder().decode(avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, getAvatar());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar1 = (Avatar) o;
        return id == avatar1.id && username.equals(avatar1.username) && Arrays.equals(avatar, avatar1.avatar);
    }

    @Override
    public String toString() {
        return "Avatar {" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", avatar='" + Base64.getEncoder().encodeToString(avatar) + '\'' +
                '}';
    }
}
