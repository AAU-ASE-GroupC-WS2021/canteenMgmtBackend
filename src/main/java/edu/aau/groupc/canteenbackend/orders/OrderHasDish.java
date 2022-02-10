package edu.aau.groupc.canteenbackend.orders;

import edu.aau.groupc.canteenbackend.DBEntity;
import edu.aau.groupc.canteenbackend.dish.Dish;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "orderHasDish")
public class OrderHasDish implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dishId")
    private Dish dish;

    @Min(1)
    private Integer count;

    public OrderHasDish() {
        // default
    }

    public OrderHasDish(Order order, Dish dish, Integer count) {
        this.order = order;
        this.dish = dish;
        this.count = count;
    }

    public Integer getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
