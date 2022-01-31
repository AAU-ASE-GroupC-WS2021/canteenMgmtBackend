package edu.aau.groupc.canteenbackend.auth;

import edu.aau.groupc.canteenbackend.entities.DBEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "auth", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"token"}),
})
public class Auth implements DBEntity {

    private static int TOKEN_VALIDITY_DURATION_MILISECONDS = 24 * 60 * 60 * 1000;

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
        this.token = generateRandomToken();
        this.timeStart = System.currentTimeMillis();
        this.timeEnd = this.timeStart + TOKEN_VALIDITY_DURATION_MILISECONDS;
    }

    public Auth(String username, String token) {
        super();
        this.username = username;
        this.token = token;
        this.timeStart = System.currentTimeMillis();
        this.timeEnd = this.timeStart + TOKEN_VALIDITY_DURATION_MILISECONDS;
    }

    public Auth(String username, long maxDuration) {
        super();
        this.username = username;
        this.token = generateRandomToken();
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

    public boolean isNotExpired() {
        long now = System.currentTimeMillis();
        return now >= timeStart && now <= timeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auth auth = (Auth) o;
        return id == auth.id && timeStart == auth.timeStart && timeEnd == auth.timeEnd && username.equals(auth.username) && token.equals(auth.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, token, timeStart, timeEnd);
    }

    @Override
    public String toString() {
        return "Auth{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                '}';
    }

    private String generateRandomToken() {
        return UUID.randomUUID().toString();
    }
}
