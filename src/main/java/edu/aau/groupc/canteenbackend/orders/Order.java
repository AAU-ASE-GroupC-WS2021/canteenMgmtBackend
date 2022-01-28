package edu.aau.groupc.canteenbackend.orders;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.aau.groupc.canteenbackend.entities.DBEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order implements DBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // TODO: Link to the user -> need to merge the user creation into this branch first, using a temporary ID field as standin
    private Integer userId;

    // TODO: Link to the canteen -> need to merge the canteen creation branch  first, using a temporary ID field as standin
    private Integer canteenId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(Integer canteenId) {
        this.canteenId = canteenId;
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
