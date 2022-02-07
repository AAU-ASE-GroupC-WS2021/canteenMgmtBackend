package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface IDishService {
    Dish findById(Integer id) throws ResponseStatusException;

    List<Dish> findAll();

    Dish create(Dish newDish);

    ResponseEntity<Object> delete(Dish toEntity);

    ResponseEntity<Object> update(Dish toEntity);

    ResponseEntity<Object> deleteAllDishes(String deleteDishVariable) ;

    List<Dish> findByDishDayAll(String dishDay);
}
