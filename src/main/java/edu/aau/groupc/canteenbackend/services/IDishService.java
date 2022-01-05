package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.dto.Dish;

import java.util.List;

public interface IDishService {
    List<Dish> findAll();

    void create(Dish newDish);
}
