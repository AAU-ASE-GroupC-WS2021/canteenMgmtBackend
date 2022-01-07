package edu.aau.groupc.canteenbackend.dao;

import edu.aau.groupc.canteenbackend.dto.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {

}