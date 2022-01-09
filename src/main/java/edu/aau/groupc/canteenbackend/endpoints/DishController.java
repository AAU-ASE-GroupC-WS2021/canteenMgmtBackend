package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.dto.DishDTO;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.services.IDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class DishController {
    private final IDishService dishService;

    @Autowired
    public DishController(IDishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping(value = "/dish")
    public List<Dish> getDishes()
    {
        return dishService.findAll();
    }

    @PostMapping(value = "/dish")
    public Dish createDish(@RequestBody DishDTO newDish)
    {
        return dishService.create(newDish.toEntity());
    }
}
