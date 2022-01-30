package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IDishService {
    List<Dish> findAll();

    Dish create(Dish newDish);

    ResponseEntity delete(Dish toEntity);

    Dish update(Dish toEntity);
}
