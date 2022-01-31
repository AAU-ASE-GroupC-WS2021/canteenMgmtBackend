package edu.aau.groupc.canteenbackend.user;

import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANTEEN_ID")
    private Canteen homeCanteen;

    public enum Type {
        OWNER,
        ADMIN,
        USER,
        GUEST,
    }

    public User() {}

    public User(String username, String password, Type type) {
        super();
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
