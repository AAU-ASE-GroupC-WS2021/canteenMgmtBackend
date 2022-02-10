package edu.aau.groupc.canteenbackend.dish.repositories;

import edu.aau.groupc.canteenbackend.dish.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> findByDishDay(Dish.DishDay day);

    List<Dish> findAllByOrderByIdDesc();

    boolean existsByName(String name);
}