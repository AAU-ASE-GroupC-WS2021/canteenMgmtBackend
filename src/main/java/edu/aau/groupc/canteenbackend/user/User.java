package edu.aau.groupc.canteenbackend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.orders.Order;
import lombok.Data;
import net.minidev.json.JSONObject;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.HashSet;

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
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

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

    public User(String username, String password, Type type, Canteen homeCanteen) {
        this(username, password, type);
        this.homeCanteen = homeCanteen;
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

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && username.equals(user.username) && password.equals(user.password) && type == user.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, type);
    }

    public String getUserSelfInfo() {
        JSONObject userObject = new JSONObject();
        userObject.appendField("username", this.username);
        userObject.appendField("type", this.type);
        return userObject.toString();
    }
}
