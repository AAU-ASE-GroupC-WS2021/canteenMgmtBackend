package edu.aau.groupc.canteenbackend.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.aau.groupc.canteenbackend.DBEntity;
import edu.aau.groupc.canteenbackend.orders.OrderHasMenu;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "menu",  uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name"}),
} )
public class Menu implements DBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    private float price;

    private MenuDay menuDay;

    public enum MenuDay {
        NOMENUDAY,
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY
    }

    @NonNull
    @ElementCollection
    List<String> menuDishNames;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<OrderHasMenu> orderHasMenus = new HashSet<>();

    public Menu() {
        // default
    }

    public Menu(@NonNull String name, float price, MenuDay menuDay, List<String> menuDishNames) {
        super();
        this.name = name;
        this.price = price;
        this.menuDishNames = menuDishNames;
        this.menuDay = menuDay;
    }

    public Integer getId() {
        return id;
    }


    @NonNull
    public String getName() {
        return name;
    }

    public Menu setName(@NonNull String name) {
        this.name = name;
        return this;
    }

    public float getPrice() {
        return price;
    }

    public Menu setPrice(float price) {
        this.price = price;
        return this;
    }
    @NonNull
    public MenuDay getMenuDay() {
        return menuDay;
    }

    public Menu setMenuDay(@NonNull MenuDay name) {
        this.menuDay = name;
        return this;
    }

    public List<String> getMenuDishNames() {
        return menuDishNames;
    }

    public Menu setMenuDishNames(List<String> menuDishNames) {
        this.menuDishNames = menuDishNames;
        return this;
    }

    public Set<OrderHasMenu> getOrderHasMenus() {
        return orderHasMenus;
    }

    public void setOrderHasMenus(Set<OrderHasMenu> orderHasMenus) {
        this.orderHasMenus = orderHasMenus;
    }
}
