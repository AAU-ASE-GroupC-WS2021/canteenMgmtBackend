package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.dto.Dish;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DishController {
    private IDishService dishService;

    @Autowired
    public DishController(IDishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping(value = "/dish")
    public List<Dish> getDishes()
    {
        List<Dish> dishes = dishService.findAll();
        return dishes;
    }

    @PostMapping(value = "/dish")
    public Dish createDish(@RequestBody Dish newDish)
    {
        dishService.create(newDish);
        return newDish;
    }
}
