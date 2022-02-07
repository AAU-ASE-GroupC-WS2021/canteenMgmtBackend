package edu.aau.groupc.canteenbackend.menu.repositories;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {


    List<Menu> findAllByOrderByIdDesc();

    List<Menu> findByMenuDay(Menu.MenuDay menuDay);
    Menu       findByName (String name);
    boolean existsByName(String name);
}