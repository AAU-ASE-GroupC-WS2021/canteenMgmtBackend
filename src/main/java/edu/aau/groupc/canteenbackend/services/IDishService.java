package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface IDishService {
    Dish findById(Integer id) throws ResponseStatusException;

    List<Dish> findAll();

    Dish create(Dish newDish);
}
