package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDishService {
    List<Dish> findAll();

    Dish create(Dish newDish);

    ResponseEntity<Object> delete(Dish toEntity);

    ResponseEntity<Object> update(Dish toEntity);

    ResponseEntity<Object> deleteAllDishes(String var) ;

    List<Dish> findByDishDayAll(String dishDay);
}
