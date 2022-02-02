package edu.aau.groupc.canteenbackend.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;
import edu.aau.groupc.canteenbackend.user.User;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order implements DBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "canteenId")
    private Canteen canteen;

    private Date pickUpDate;
    @Column(columnDefinition = "boolean default false")
    private boolean reserveTable;

    /*
     * many-to-many connection to the dishes (through the assoziation entity)
     * required since one order may contain many different dishes and one dish may be in multiple orders
     * This assoziation is also modelled in an entity, since one dish may be ordered multiple times
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OrderHasDish> orderHasDishes = new HashSet<>();

    // TODO: many-to-many connection to the menus, menus do not exist yet

    public Order() {
        // default
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Canteen getCanteen() {
        return canteen;
    }

    public void setCanteen(Canteen canteen) {
        this.canteen = canteen;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public boolean isReserveTable() {
        return reserveTable;
    }

    public void setReserveTable(boolean reserveTable) {
        this.reserveTable = reserveTable;
    }

    public Set<OrderHasDish> getOrderHasDishes() {
        return orderHasDishes;
    }

    public void setOrderHasDishes(Set<OrderHasDish> orderHasDishes) {
        this.orderHasDishes = orderHasDishes;
    }

    public void addOrderHasDish(OrderHasDish orderHasDish) {
        this.orderHasDishes.add(orderHasDish);
    }
}
