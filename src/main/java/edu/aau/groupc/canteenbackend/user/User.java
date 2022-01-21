package edu.aau.groupc.canteenbackend.user;

import edu.aau.groupc.canteenbackend.dto.DTO;
import edu.aau.groupc.canteenbackend.entities.DBEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
})
public class User implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private Type type;

    public User(String username, String password, Type type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public enum Type {
        OWNER,
        ADMIN,
        USER,
        GUEST,
    }

}
