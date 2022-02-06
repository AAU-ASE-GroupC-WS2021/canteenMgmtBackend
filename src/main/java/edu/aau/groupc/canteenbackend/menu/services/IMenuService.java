package edu.aau.groupc.canteenbackend.menu.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMenuService {


//    List<Dish> findAllMenus();
//
//    List<Dish> findMenuByDay( Dish.DishDay day);

    List<Menu> findAll();

    Menu create(Menu newMenu);

    ResponseEntity<Object> delete(Menu newMenu);

    ResponseEntity<Object> update(Menu newMenu);


    List<Menu> findByMenuDay(Menu.MenuDay menuDay);
}
