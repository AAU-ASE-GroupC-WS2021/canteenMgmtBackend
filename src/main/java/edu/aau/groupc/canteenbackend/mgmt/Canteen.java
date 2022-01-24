package edu.aau.groupc.canteenbackend.mgmt;

import edu.aau.groupc.canteenbackend.entities.DBEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "canteen")
public class Canteen implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String address;
    private int numTables;

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
}
