package edu.aau.groupc.canteenbackend.avatar;

import edu.aau.groupc.canteenbackend.entities.DBEntity;
import lombok.Data;

import javax.persistence.*;
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
        this.avatar = Base64.getDecoder().decode(avatar);;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, avatar);
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
