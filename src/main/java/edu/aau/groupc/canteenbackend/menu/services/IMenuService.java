package edu.aau.groupc.canteenbackend.menu.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMenuService {


    List<Dish> findAllMenus();

    List<Dish> findMenuByDay( Dish.DishDay day);


}
