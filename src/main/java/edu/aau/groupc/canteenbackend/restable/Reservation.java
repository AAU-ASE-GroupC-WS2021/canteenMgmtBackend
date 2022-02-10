package edu.aau.groupc.canteenbackend.restable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "reservation")
public class Reservation implements DBEntity {

    @Id
    @Column(name = "RESERVATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resId;

    private Date reservationDate;

    @OneToOne(mappedBy = "reservation", fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Availability> availability = new HashSet<>();


    public Reservation() {
        // default
    }

    public Reservation(Date reservationDate) {
        super();
        this.reservationDate = reservationDate;
    }

    public Integer getResId() {
        return resId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Availability> getAvailability() {
        return availability;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

}
