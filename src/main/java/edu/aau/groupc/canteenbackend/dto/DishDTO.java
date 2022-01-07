package edu.aau.groupc.canteenbackend.dto;

import edu.aau.groupc.canteenbackend.entities.Dish;

public class DishDTO implements DTO {
    private Integer id;
    private String name;
    private float price;
    private Dish.Type type;

    public DishDTO() {
    }

    public DishDTO(Integer id, String name, float price, Dish.Type type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Dish.Type getType() {
        return type;
    }

    public void setType(Dish.Type type) {
        this.type = type;
    }

    public Dish toEntity() {
        return new Dish(getName(), getPrice(), getType());
    }
}
