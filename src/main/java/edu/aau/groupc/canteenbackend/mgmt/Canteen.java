package edu.aau.groupc.canteenbackend.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.orders.Order;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "canteen")
public class Canteen implements DBEntity {

    @Id
    @Column(name = "CANTEEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    private int numTables;
    @OneToMany(mappedBy = "canteen", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    public Canteen() {
        // default
    }

    public Canteen(String name, String address, int numTables) {
        super();
        this.name = name;
        this.address = address;
        this.numTables = numTables;
    }

    public Integer getId() {
        return id;
    }

    public Canteen setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Canteen setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Canteen setAddress(String address) {
        this.address = address;
        return this;
    }

    public int getNumTables() {
        return numTables;
    }

    public Canteen setNumTables(int numTables) {
        this.numTables = numTables;
        return this;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
