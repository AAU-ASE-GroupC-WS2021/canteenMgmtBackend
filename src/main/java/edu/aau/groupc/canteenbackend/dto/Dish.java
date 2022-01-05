package edu.aau.groupc.canteenbackend.dto;

public class Dish {
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
