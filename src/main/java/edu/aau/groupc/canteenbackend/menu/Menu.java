package edu.aau.groupc.canteenbackend.menu;

import edu.aau.groupc.canteenbackend.entities.DBEntity;
import edu.aau.groupc.canteenbackend.entities.Dish;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.List;


@Entity
public class Menu implements DBEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Dish.DishDay menuDay;

    @OneToMany
    List<Dish> menuDishes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu() {
        // default
    }

    public Menu(Dish.DishDay menuDay, List<Dish> menuDishes) {
        super();
        this.menuDishes = menuDishes;
        this.menuDay = menuDay;
    }


    public Dish.DishDay getMenuDay() {
        return menuDay;
    }

    public Menu setMenuDay(@NonNull Dish.DishDay name) {
        this.menuDay = name;
        return this;
    }

    public Menu setMenuDishes(List<Dish> menuDishes) {
        this.menuDishes = menuDishes;
        return this;
    }


}
