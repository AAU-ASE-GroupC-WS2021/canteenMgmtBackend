package edu.aau.groupc.canteenbackend.entities;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "dish")
public class Dish implements DBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;
    private float price;
    @NonNull
    private Type type;

    public enum Type {
        STARTER,
        MAIN,
        DESSERT
    }

    private DishDay dishDay;

    public enum DishDay {
        NOMENUDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }

    public Dish() {
        // default
    }

    public Dish(@NonNull String name, float price, @NonNull Type type, @NonNull DishDay dishDay) {
        super();
        this.name = name;
        this.price = price;
        this.type = type;
        this.dishDay = dishDay;
    }

    public Integer getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public Dish setName(@NonNull String name) {
        this.name = name;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public Dish setPrice(float price) {
        this.price = price;
        return this;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    public Dish setType(@NonNull Type type) {
        this.type = type;
        return this;
    }

    @NonNull
    public DishDay getDishDay() {
        return dishDay;
    }

    public Dish setDishDay(@NonNull DishDay dishDay) {
        this.dishDay = dishDay;
        return this;
    }
}
