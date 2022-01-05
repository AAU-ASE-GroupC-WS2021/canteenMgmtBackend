package edu.aau.groupc.canteenbackend.services;

import edu.aau.groupc.canteenbackend.dto.Dish;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DishService implements IDishService {
    private final ArrayList<Dish> dishes;

    public DishService() {
        dishes = new ArrayList<>();
        dishes.add(new Dish("Salad", 1.5f, Dish.Type.STARTER));
        dishes.add(new Dish("Cheese Burger", 4f, Dish.Type.MAIN));
    }

    @Override
    public List<Dish> findAll() {
        return dishes;
    }

    @Override
    public void create(Dish newDish) {
        dishes.add(newDish);
    }
}
