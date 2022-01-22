package edu.aau.groupc.canteenbackend.auth;

import edu.aau.groupc.canteenbackend.entities.DBEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "auth", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username", "token"}),
})
public class Auth implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;
    private String token;
    private long timeStart;
    private long timeEnd;

    public Auth() {}

    public Auth(String username) {
        super();
        this.username = username;
        this.token = username + System.currentTimeMillis();
        this.timeStart = System.currentTimeMillis();
        this.timeEnd = this.timeStart + 60 * 60 * 1000;
    }

    public Auth(String username, String token) {
        super();
        this.username = username;
        this.token = token;
        this.timeStart = 0;
        this.timeEnd = 0;
    }

    public Auth(String username, long maxDuration) {
        super();
        this.username = username;
        this.token = username + System.currentTimeMillis();
        this.timeStart = System.currentTimeMillis();
        this.timeEnd = this.timeStart + maxDuration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(long maxDuration) {
        this.timeEnd = this.timeStart + maxDuration;
    }
}
