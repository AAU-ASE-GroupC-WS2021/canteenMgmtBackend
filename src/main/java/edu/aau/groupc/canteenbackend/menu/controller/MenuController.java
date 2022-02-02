package edu.aau.groupc.canteenbackend.menu.controller;

import edu.aau.groupc.canteenbackend.entities.Dish;
import edu.aau.groupc.canteenbackend.menu.services.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
public class MenuController {
    private final IMenuService menuService;

    @Autowired
    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping(value = "/menu")
    public List<Dish> getMenuDishes(@RequestParam(required = false) @Valid Dish.DishDay menuDay)
    {
        if(menuDay !=null)
            return menuService.findMenuByDay(menuDay);
        else
            return menuService.findAllMenus();
    }


}
