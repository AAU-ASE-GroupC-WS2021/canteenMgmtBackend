package edu.aau.groupc.canteenbackend.avatar;

import edu.aau.groupc.canteenbackend.entities.DBEntity;
import lombok.Data;

import javax.persistence.*;
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
    private String avatar;

    public Avatar() {}

    public Avatar(String username, String avatar) {
        super();
        this.username = username;
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
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
