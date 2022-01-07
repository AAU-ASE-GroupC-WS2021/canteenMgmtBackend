package edu.aau.groupc.canteenbackend.dto;

import javax.persistence.*;

@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private float price;
    private Type type;

    public enum Type {
        STARTER,
        MAIN,
        DESSERT
    }

    public Dish() {
        // default
    }

    public Dish(String name, float price, Type type) {
        super();
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
