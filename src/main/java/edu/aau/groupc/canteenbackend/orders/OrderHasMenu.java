package edu.aau.groupc.canteenbackend.orders;

import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.menu.Menu;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "orderHasMenu")
public class OrderHasMenu implements DBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId")
    private Menu menu;

    @Min(1)
    private Integer count;

    public OrderHasMenu() {
        // default
    }

    public OrderHasMenu(Order order, Menu menu, Integer count) {
        this.order = order;
        this.menu = menu;
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

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
