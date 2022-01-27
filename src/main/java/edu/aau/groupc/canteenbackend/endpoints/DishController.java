package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.dto.DishDTO;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.services.IDishService;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
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

    @Secured(User.Type.USER)
    @PostMapping(value = "/dish")
    public Dish createDish(@Valid @RequestBody DishDTO newDish)
    {
        return dishService.create(newDish.toEntity());
    }
}
