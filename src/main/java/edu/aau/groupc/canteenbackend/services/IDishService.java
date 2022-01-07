package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.entities.Dish;

import java.util.List;

public interface IDishService {
    List<Dish> findAll();

    Dish create(Dish newDish);
}
