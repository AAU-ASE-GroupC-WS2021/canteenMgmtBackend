package edu.aau.groupc.canteenbackend.dao;

import edu.aau.groupc.canteenbackend.entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {

    List<Dish> findBydishDay(Dish.DishDay day);
}