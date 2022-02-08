package edu.aau.groupc.canteenbackend.endpoints;

import edu.aau.groupc.canteenbackend.auth.security.Secured;
import edu.aau.groupc.canteenbackend.dto.DishDTO;
import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.services.IDishService;
import edu.aau.groupc.canteenbackend.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public List<Dish> getMenus(@RequestParam(required = false) @Valid String dishDay)
    {
        if(dishDay == null)
            return dishService.findAll();
        else
            return dishService.findByDishDayAll(dishDay);
    }


    @Secured(User.Type.ADMIN)
    @PostMapping(value = "/dish")
    public Dish createDish(@Valid @RequestBody DishDTO newDish)
    {
        return dishService.create(newDish.toEntity());
    }

    @Secured(User.Type.ADMIN)
    @PutMapping(value = "/dish")
    public ResponseEntity<Object> update(@Valid @RequestBody DishDTO updateDish)
    {
        return dishService.update(updateDish.toEntity());
    }
    @Secured(User.Type.ADMIN)
    @DeleteMapping (value = "/dish")
    public ResponseEntity<Object> delete(@Valid @RequestBody String deleteDishName)
    {
        return dishService.delete(deleteDishName);
    }
}
