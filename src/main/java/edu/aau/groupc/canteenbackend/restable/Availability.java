package edu.aau.groupc.canteenbackend.restable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.mgmt.Canteen;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "availability")
public class Availability implements DBEntity {

    @Id
    @Column(name = "slot")
    private Integer slot;

    @NotNull
    private int numAvailableTables;

    @OneToMany(mappedBy = "canteen", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Canteen> canteens = new HashSet<>();



    public Availability() {

    }

    public Availability(int slot, int numAvailableTables) {
        super();
        this.slot = slot;
        this.numAvailableTables = numAvailableTables;
    }

    public Integer getSlot(){
        return slot;
    }
    public Availability setSlot(Integer slot) {
        this.slot = slot;
        return this;
    }

    public int getNumAvailableTables() {
        return numAvailableTables;
    }

    public Availability cancelReservation(int slot){
        this.numAvailableTables = this.numAvailableTables + 1;
        return this;
    }

    public Availability setNumAvailableTables (int numAvailableTables) {
        this.numAvailableTables = numAvailableTables;
        return this;
    }


    public Set<Canteen> getCanteens() {
        return canteens;
    }

    public void setCanteens(Set<Canteen> canteens) {
        this.canteens = canteens;
    }

}
