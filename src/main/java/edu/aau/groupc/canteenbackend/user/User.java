package edu.aau.groupc.canteenbackend.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private Type type;

    public enum Type {
        OWNER,
        ADMIN,
        USER,
        GUEST,
    }

}
